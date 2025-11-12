-- =====================================================================================================================
-- SISTEMA TRANSPORT - BASE DE DATOS COMPLETA
-- Creado para: TransPort - Sistema de Control Empresarial Multi-Giro
-- Tecnología: SQL Server 2019+
-- Fecha: Octubre 2025
-- Estado: PRODUCTION READY ✅
-- =====================================================================================================================

create database TransPort;
use TransPort;
-- =====================================================================================================================
-- FASE 1: TABLAS MAESTRAS (SIN DEPENDENCIAS)
-- =====================================================================================================================

-- Tabla: GIRO_NEGOCIO - División de negocio de la empresa
CREATE TABLE giro_negocio (
    id_giro_negocio INT PRIMARY KEY IDENTITY(1,1),
    codigo_giro NVARCHAR(10) UNIQUE NOT NULL,
    nombre_giro NVARCHAR(100) NOT NULL,
    descripcion NVARCHAR(255),
    tipo_giro NVARCHAR(50) NOT NULL CHECK (tipo_giro IN ('EXTRACCION', 'PROCESO', 'VENTA_MAQUINARIA', 'TRANSPORTE', 'CONSTRUCCION')),
    presupuesto_anual DECIMAL(15,2) DEFAULT 0,
    responsable NVARCHAR(100),
    activo BIT DEFAULT 1,
    fecha_creacion DATETIME DEFAULT GETDATE()
);

-- Tabla: PERSONA - Maestro centralizado (Clientes, Empleados, Proveedores)
CREATE TABLE persona (
    id_persona INT PRIMARY KEY IDENTITY(1,1),
    tipo_persona NVARCHAR(20) NOT NULL CHECK (tipo_persona IN ('NATURAL', 'JURIDICA')),
    primer_nombre NVARCHAR(50),
    segundo_nombre NVARCHAR(50),
    tercer_nombre NVARCHAR(50),
    primer_apellido NVARCHAR(50),
    segundo_apellido NVARCHAR(50),
    apellido_casada NVARCHAR(50),
    razon_social NVARCHAR(200),
    numero_documento NVARCHAR(20) UNIQUE NOT NULL,
    nit NVARCHAR(15),
    dpi NVARCHAR(15),
    ruc NVARCHAR(20),
    telefono NVARCHAR(20),
    telefono_celular NVARCHAR(20),
    email NVARCHAR(100),
    direccion NVARCHAR(255),
    departamento NVARCHAR(50),
    pais NVARCHAR(50) DEFAULT 'Guatemala',
    fecha_nacimiento DATE,
    nombre_empresa NVARCHAR(100),
    banco NVARCHAR(100),
    cuenta_bancaria NVARCHAR(50),
    tipo_cuenta NVARCHAR(20),
    fecha_creacion DATETIME DEFAULT GETDATE()
);

-- Tabla: ESTADO_EMPLEADO - Estados posibles para empleados
CREATE TABLE estado_empleado (
    id_estado_empleado INT PRIMARY KEY IDENTITY(1,1),
    estado NVARCHAR(50) NOT NULL UNIQUE,
    descripcion NVARCHAR(255)
);

-- Tabla: TIPO_ALERTA - Tipos de alertas del sistema
CREATE TABLE tipo_alerta (
    id_tipo_alerta INT PRIMARY KEY IDENTITY(1,1),
    nombre_alerta NVARCHAR(100) NOT NULL UNIQUE,
    descripcion NVARCHAR(255),
    prioridad NVARCHAR(20) DEFAULT 'MEDIA' CHECK (prioridad IN ('BAJA', 'MEDIA', 'ALTA', 'URGENTE')),
    activo BIT DEFAULT 1
);

-- Tabla: METODO_PAGO - Métodos de pago disponibles
CREATE TABLE metodo_pago (
    id_metodo_pago INT PRIMARY KEY IDENTITY(1,1),
    nombre_metodo NVARCHAR(50) NOT NULL UNIQUE,
    descripcion NVARCHAR(255),
    activo BIT DEFAULT 1
);

-- Tabla: ESTADO_ORDEN - Estados posibles de órdenes
CREATE TABLE estado_orden (
    id_estado_orden INT PRIMARY KEY IDENTITY(1,1),
    estado NVARCHAR(50) NOT NULL UNIQUE,
    descripcion NVARCHAR(255),
    es_final BIT DEFAULT 0
);

-- =====================================================================================================================
-- FASE 2: TABLAS DEPENDIENTES DE MAESTRAS (Nivel 1)
-- =====================================================================================================================

-- Tabla: SUCURSAL - Ubicaciones de cada giro de negocio
CREATE TABLE sucursal (
    id_sucursal INT PRIMARY KEY IDENTITY(1,1),
    id_giro_negocio INT NOT NULL,
    codigo_sucursal NVARCHAR(10) UNIQUE NOT NULL,
    nombre_sucursal NVARCHAR(100) NOT NULL,
    departamento NVARCHAR(50) NOT NULL,
    direccion NVARCHAR(255),
    telefono NVARCHAR(20),
    email NVARCHAR(100),
    fecha_apertura DATE,
    fecha_cierre DATE,
    activa BIT DEFAULT 1,
    CONSTRAINT FK_Sucursal_GiroNegocio FOREIGN KEY (id_giro_negocio) REFERENCES giro_negocio(id_giro_negocio)
);

-- Tabla: CLIENTE - Clientes de la empresa
CREATE TABLE cliente (
    id_cliente INT PRIMARY KEY IDENTITY(1,1),
    id_persona INT NOT NULL,
    fecha_registro DATE DEFAULT GETDATE(),
    categoria NVARCHAR(50) DEFAULT 'REGULAR' CHECK (categoria IN ('REGULAR', 'PREMIUM', 'VIP')),
    limite_credito DECIMAL(15,2) DEFAULT 0,
    activo BIT DEFAULT 1,
    CONSTRAINT FK_Cliente_Persona FOREIGN KEY (id_persona) REFERENCES persona(id_persona)
);

-- Tabla: PROVEEDOR - Proveedores de la empresa (NUEVA)
CREATE TABLE proveedor (
    id_proveedor INT PRIMARY KEY IDENTITY(1,1),
    id_persona INT NOT NULL,
    nombre_comercial NVARCHAR(200),
    categoria_proveedor NVARCHAR(50) DEFAULT 'GENERAL',
    calificacion DECIMAL(3,2) DEFAULT 0,
    dias_plazo INT DEFAULT 30,
    limite_credito DECIMAL(15,2) DEFAULT 0,
    contacto_principal NVARCHAR(100),
    telefono_contacto NVARCHAR(20),
    email_contacto NVARCHAR(100),
    activo BIT DEFAULT 1,
    fecha_registro DATETIME DEFAULT GETDATE(),
    CONSTRAINT FK_Proveedor_Persona FOREIGN KEY (id_persona) REFERENCES persona(id_persona)
);

-- Tabla: EMPLEADO - Empleados de la empresa
CREATE TABLE empleado (
    id_empleado INT PRIMARY KEY IDENTITY(1,1),
    id_persona INT NOT NULL,
    id_sucursal INT NOT NULL,
    id_estado_empleado INT NOT NULL,
    codigo_empleado NVARCHAR(20) UNIQUE NOT NULL,
    fecha_ingreso DATE NOT NULL,
    salario_base DECIMAL(10,2) NOT NULL,
    fecha_egreso DATE,
    cargo NVARCHAR(100),
    departamento NVARCHAR(50),
    numero_licencia NVARCHAR(50),
    categoria_licencia NVARCHAR(20),
    fecha_vencimiento_licencia DATE,
    activo BIT DEFAULT 1,
    CONSTRAINT FK_Empleado_Persona FOREIGN KEY (id_persona) REFERENCES persona(id_persona),
    CONSTRAINT FK_Empleado_Sucursal FOREIGN KEY (id_sucursal) REFERENCES sucursal(id_sucursal),
    CONSTRAINT FK_Empleado_Estado FOREIGN KEY (id_estado_empleado) REFERENCES estado_empleado(id_estado_empleado)
);

-- Tabla: USUARIO - Usuarios del sistema (autenticación y autorización)
CREATE TABLE usuario (
    id_usuario INT PRIMARY KEY IDENTITY(1,1),
    id_empleado INT NOT NULL,
    nombre_usuario NVARCHAR(50) UNIQUE NOT NULL,
    password_hash NVARCHAR(255) NOT NULL,
    email NVARCHAR(100),
    id_giro_negocio INT,
    rol NVARCHAR(50) NOT NULL CHECK (rol IN ('ADMIN', 'GERENTE', 'OPERADOR', 'VENDEDOR', 'SUPERVISOR', 'CONTADOR')),
    activo BIT DEFAULT 1,
    fecha_creacion DATETIME DEFAULT GETDATE(),
    fecha_ultimo_login DATETIME,
    intentos_fallidos INT DEFAULT 0,
    bloqueado BIT DEFAULT 0,
    CONSTRAINT FK_Usuario_Empleado FOREIGN KEY (id_empleado) REFERENCES empleado(id_empleado),
    CONSTRAINT FK_Usuario_GiroNegocio FOREIGN KEY (id_giro_negocio) REFERENCES giro_negocio(id_giro_negocio)
);

-- Tabla: CATEGORIA_INVENTARIO - Categorías de productos por giro
CREATE TABLE categoria_inventario (
    id_categoria INT PRIMARY KEY IDENTITY(1,1),
    id_giro_negocio INT NOT NULL,
    nombre_categoria NVARCHAR(100) NOT NULL,
    descripcion NVARCHAR(255),
    activo BIT DEFAULT 1,
    CONSTRAINT FK_Categoria_GiroNegocio FOREIGN KEY (id_giro_negocio) REFERENCES giro_negocio(id_giro_negocio)
);

-- =====================================================================================================================
-- FASE 3: TABLAS OPERATIVAS (Nivel 2 - Transaccionales)
-- =====================================================================================================================

-- Tabla: INVENTARIO - Control de inventario con máximos y mínimos
CREATE TABLE inventario (
    id_inventario INT PRIMARY KEY IDENTITY(1,1),
    id_categoria INT NOT NULL,
    id_sucursal INT NOT NULL,
    id_proveedor INT,
    tipo_producto NVARCHAR(30) NOT NULL CHECK (tipo_producto IN ('MATERIA_PRIMA', 'PRODUCTO_TERMINADO', 'MAQUINARIA', 'EQUIPO', 'VEHICULO')),
    codigo_producto NVARCHAR(50) UNIQUE NOT NULL,
    nombre_producto NVARCHAR(200) NOT NULL,
    descripcion NVARCHAR(500),
    codigo_barras NVARCHAR(50) UNIQUE,
    numero_parte NVARCHAR(50),
    unidad_medida NVARCHAR(20),
    existencia_actual DECIMAL(12,3) DEFAULT 0,
    existencia_minima DECIMAL(12,3) DEFAULT 0,
    existencia_maxima DECIMAL(12,3),
    costo_promedio DECIMAL(12,2) DEFAULT 0,
    precio_venta DECIMAL(12,2) DEFAULT 0,
    lote NVARCHAR(50),
    fecha_vencimiento DATE,
    ubicacion_almacen NVARCHAR(100),
    fecha_ultimo_movimiento DATETIME,
    dias_sin_movimiento INT DEFAULT 0,
    activo BIT DEFAULT 1,
    fecha_creacion DATETIME DEFAULT GETDATE(),
    fecha_actualizacion DATETIME DEFAULT GETDATE(),
    CONSTRAINT FK_Inventario_Categoria FOREIGN KEY (id_categoria) REFERENCES categoria_inventario(id_categoria),
    CONSTRAINT FK_Inventario_Sucursal FOREIGN KEY (id_sucursal) REFERENCES sucursal(id_sucursal),
    CONSTRAINT FK_Inventario_Proveedor FOREIGN KEY (id_proveedor) REFERENCES proveedor(id_proveedor)
);

-- Tabla: DISPOSITIVO_GPS - Dispositivos GPS para vehículos y maquinaria
CREATE TABLE dispositivo_gps (
    id_dispositivo INT PRIMARY KEY IDENTITY(1,1),
    id_inventario INT NOT NULL,
    codigo_dispositivo NVARCHAR(50) UNIQUE NOT NULL,
    placa_vehiculo NVARCHAR(20) UNIQUE,
    marca_modelo NVARCHAR(100),
    numero_motor NVARCHAR(50),
    numero_chasis NVARCHAR(50),
    capacidad_carga DECIMAL(12,2),
    fecha_ultimo_mantenimiento DATE,
    proximo_mantenimiento DATE,
    estado_vehiculo NVARCHAR(50) DEFAULT 'OPERATIVO' CHECK (estado_vehiculo IN ('OPERATIVO', 'MANTENIMIENTO', 'INACTIVO')),
    proveedor_gps NVARCHAR(50),
    sim_card NVARCHAR(50),
    activo BIT DEFAULT 1,
    fecha_creacion DATETIME DEFAULT GETDATE(),
    CONSTRAINT FK_DispositivoGPS_Inventario FOREIGN KEY (id_inventario) REFERENCES inventario(id_inventario)
);

-- Tabla: MONITOREO_GPS - Histórico de monitoreo GPS
CREATE TABLE monitoreo_gps (
    id_monitoreo INT PRIMARY KEY IDENTITY(1,1),
    id_dispositivo INT NOT NULL,
    fecha_hora DATETIME DEFAULT GETDATE(),
    latitud DECIMAL(10,6),
    longitud DECIMAL(10,6),
    velocidad DECIMAL(8,2),
    direccion NVARCHAR(100),
    estado_operativo NVARCHAR(50),
    odometro DECIMAL(12,2),
    nivel_combustible DECIMAL(5,2),
    temperatura DECIMAL(5,2),
    CONSTRAINT FK_MonitoreoGPS_Dispositivo FOREIGN KEY (id_dispositivo) REFERENCES dispositivo_gps(id_dispositivo)
);

-- Tabla: ORDEN - Órdenes unificadas (Pedidos cliente, internos, compra, trabajo)
CREATE TABLE orden (
    id_orden INT PRIMARY KEY IDENTITY(1,1),
    tipo_orden NVARCHAR(30) NOT NULL CHECK (tipo_orden IN ('PEDIDO_CLIENTE', 'PEDIDO_INTERNO', 'ORDEN_TRABAJO', 'ORDEN_COMPRA')),
    numero_orden NVARCHAR(50) UNIQUE NOT NULL,
    id_giro_negocio_origen INT,
    id_giro_negocio_destino INT,
    id_giro_negocio_ejecutor INT,
    id_sucursal_origen INT,
    id_sucursal_destino INT,
    id_cliente INT,
    id_proveedor INT,
    id_estado_orden INT NOT NULL,
    id_empleado_responsable INT,
    fecha_creacion DATETIME DEFAULT GETDATE(),
    fecha_requerida DATE,
    fecha_entrega DATE,
    fecha_inicio_estimada DATE,
    fecha_inicio_real DATE,
    duracion_estimada INT,
    duracion_real INT,
    prioridad NVARCHAR(20) DEFAULT 'NORMAL' CHECK (prioridad IN ('BAJA', 'NORMAL', 'ALTA', 'URGENTE')),
    porcentaje_avance INT DEFAULT 0 CHECK (porcentaje_avance >= 0 AND porcentaje_avance <= 100),
    subtotal DECIMAL(15,2) DEFAULT 0,
    impuestos DECIMAL(15,2) DEFAULT 0,
    descuentos DECIMAL(15,2) DEFAULT 0,
    total DECIMAL(15,2) DEFAULT 0,
    observaciones NVARCHAR(500),
    usuario_creacion NVARCHAR(100),
    CONSTRAINT FK_Orden_GiroOrigen FOREIGN KEY (id_giro_negocio_origen) REFERENCES giro_negocio(id_giro_negocio),
    CONSTRAINT FK_Orden_GiroDestino FOREIGN KEY (id_giro_negocio_destino) REFERENCES giro_negocio(id_giro_negocio),
    CONSTRAINT FK_Orden_GiroEjecutor FOREIGN KEY (id_giro_negocio_ejecutor) REFERENCES giro_negocio(id_giro_negocio),
    CONSTRAINT FK_Orden_SucursalOrigen FOREIGN KEY (id_sucursal_origen) REFERENCES sucursal(id_sucursal),
    CONSTRAINT FK_Orden_SucursalDestino FOREIGN KEY (id_sucursal_destino) REFERENCES sucursal(id_sucursal),
    CONSTRAINT FK_Orden_Cliente FOREIGN KEY (id_cliente) REFERENCES cliente(id_cliente),
    CONSTRAINT FK_Orden_Proveedor FOREIGN KEY (id_proveedor) REFERENCES proveedor(id_proveedor),
    CONSTRAINT FK_Orden_Estado FOREIGN KEY (id_estado_orden) REFERENCES estado_orden(id_estado_orden),
    CONSTRAINT FK_Orden_EmpleadoResponsable FOREIGN KEY (id_empleado_responsable) REFERENCES empleado(id_empleado)
);

-- Tabla: DETALLE_ORDEN - Líneas de detalle de órdenes
CREATE TABLE detalle_orden (
    id_detalle_orden INT PRIMARY KEY IDENTITY(1,1),
    id_orden INT NOT NULL,
    id_inventario INT NOT NULL,
    cantidad_solicitada DECIMAL(12,3) NOT NULL,
    cantidad_despachada DECIMAL(12,3) DEFAULT 0,
    precio_unitario DECIMAL(12,2) NOT NULL,
    subtotal DECIMAL(15,2) NOT NULL,
    estado_detalle NVARCHAR(50) DEFAULT 'PENDIENTE' CHECK (estado_detalle IN ('PENDIENTE', 'PARCIAL', 'COMPLETADA', 'CANCELADA')),
    observaciones NVARCHAR(255),
    CONSTRAINT FK_DetalleOrden_Orden FOREIGN KEY (id_orden) REFERENCES orden(id_orden),
    CONSTRAINT FK_DetalleOrden_Inventario FOREIGN KEY (id_inventario) REFERENCES inventario(id_inventario)
);

-- Tabla: REMISIÓN - Detalles de envío de órdenes (NUEVA)
CREATE TABLE remision (
    id_remision INT PRIMARY KEY IDENTITY(1,1),
    id_orden INT NOT NULL,
    numero_remision NVARCHAR(50) UNIQUE NOT NULL,
    fecha_emision DATETIME DEFAULT GETDATE(),
    id_dispositivo INT,
    id_empleado_responsable INT,
    id_sucursal_origen INT NOT NULL,
    id_sucursal_destino INT,
    descripcion_carga NVARCHAR(500),
    peso_total DECIMAL(12,2),
    volumen_total DECIMAL(12,3),
    estado NVARCHAR(50) DEFAULT 'PENDIENTE_DESPACHO' CHECK (estado IN ('PENDIENTE_DESPACHO', 'DESPACHADA', 'EN_TRANSITO', 'ENTREGADA', 'PARCIAL', 'RECHAZADA')),
    fecha_entrega_esperada DATE,
    fecha_entrega_real DATETIME,
    observaciones NVARCHAR(500),
    firma_responsable NVARCHAR(100),
    CONSTRAINT FK_Remision_Orden FOREIGN KEY (id_orden) REFERENCES orden(id_orden),
    CONSTRAINT FK_Remision_Dispositivo FOREIGN KEY (id_dispositivo) REFERENCES dispositivo_gps(id_dispositivo),
    CONSTRAINT FK_Remision_EmpleadoResponsable FOREIGN KEY (id_empleado_responsable) REFERENCES empleado(id_empleado),
    CONSTRAINT FK_Remision_SucursalOrigen FOREIGN KEY (id_sucursal_origen) REFERENCES sucursal(id_sucursal),
    CONSTRAINT FK_Remision_SucursalDestino FOREIGN KEY (id_sucursal_destino) REFERENCES sucursal(id_sucursal)
);

-- Tabla: MOVIMIENTO_INVENTARIO - Auditoría de cambios de inventario (NUEVA)
CREATE TABLE movimiento_inventario (
    id_movimiento INT PRIMARY KEY IDENTITY(1,1),
    id_inventario INT NOT NULL,
    tipo_movimiento NVARCHAR(30) NOT NULL CHECK (tipo_movimiento IN ('ENTRADA', 'SALIDA', 'AJUSTE', 'DEVOLUCION', 'PERDIDA')),
    cantidad DECIMAL(12,3) NOT NULL,
    cantidad_anterior DECIMAL(12,3),
    cantidad_nueva DECIMAL(12,3),
    id_orden INT,
    id_usuario INT,
    motivo NVARCHAR(500),
    fecha_movimiento DATETIME DEFAULT GETDATE(),
    CONSTRAINT FK_MovInv_Inventario FOREIGN KEY (id_inventario) REFERENCES inventario(id_inventario),
    CONSTRAINT FK_MovInv_Orden FOREIGN KEY (id_orden) REFERENCES orden(id_orden),
    CONSTRAINT FK_MovInv_Usuario FOREIGN KEY (id_usuario) REFERENCES usuario(id_usuario)
);

-- Tabla: ALERTA_SISTEMA - Alertas del sistema
CREATE TABLE alerta_sistema (
    id_alerta_sistema INT PRIMARY KEY IDENTITY(1,1),
    id_tipo_alerta INT NOT NULL,
    id_usuario_destino INT,
    id_giro_negocio INT,
    id_dispositivo INT,
    id_orden INT,
    titulo NVARCHAR(200) NOT NULL,
    descripcion NVARCHAR(500),
    fecha_creacion DATETIME DEFAULT GETDATE(),
    fecha_leida DATETIME,
    prioridad NVARCHAR(20) DEFAULT 'MEDIA' CHECK (prioridad IN ('BAJA', 'MEDIA', 'ALTA', 'URGENTE')),
    leida BIT DEFAULT 0,
    accion_requerida NVARCHAR(100),
    CONSTRAINT FK_Alerta_Tipo FOREIGN KEY (id_tipo_alerta) REFERENCES tipo_alerta(id_tipo_alerta),
    CONSTRAINT FK_Alerta_Usuario FOREIGN KEY (id_usuario_destino) REFERENCES usuario(id_usuario),
    CONSTRAINT FK_Alerta_GiroNegocio FOREIGN KEY (id_giro_negocio) REFERENCES giro_negocio(id_giro_negocio),
    CONSTRAINT FK_Alerta_Dispositivo FOREIGN KEY (id_dispositivo) REFERENCES dispositivo_gps(id_dispositivo),
    CONSTRAINT FK_Alerta_Orden FOREIGN KEY (id_orden) REFERENCES orden(id_orden)
);

-- Tabla: FACTURA - Facturas generadas
CREATE TABLE factura (
    id_factura INT PRIMARY KEY IDENTITY(1,1),
    id_orden INT,
    numero_factura NVARCHAR(50) UNIQUE NOT NULL,
    fecha_emision DATE DEFAULT GETDATE(),
    fecha_vencimiento DATE,
    subtotal DECIMAL(15,2) DEFAULT 0,
    impuestos DECIMAL(15,2) DEFAULT 0,
    descuentos DECIMAL(15,2) DEFAULT 0,
    total DECIMAL(15,2) DEFAULT 0,
    estado NVARCHAR(50) DEFAULT 'PENDIENTE' CHECK (estado IN ('PENDIENTE', 'PAGADA', 'PARCIAL', 'CANCELADA', 'ANULADA')),
    id_metodo_pago INT,
    observaciones NVARCHAR(500),
    usuario_creacion NVARCHAR(100),
    CONSTRAINT FK_Factura_Orden FOREIGN KEY (id_orden) REFERENCES orden(id_orden),
    CONSTRAINT FK_Factura_MetodoPago FOREIGN KEY (id_metodo_pago) REFERENCES metodo_pago(id_metodo_pago)
);

-- Tabla: DOCUMENTO_ASOCIADO - Documentos vinculados a órdenes (NUEVA)
CREATE TABLE documento_asociado (
    id_documento INT PRIMARY KEY IDENTITY(1,1),
    id_orden INT NOT NULL,
    tipo_documento NVARCHAR(50) NOT NULL CHECK (tipo_documento IN ('COTIZACION', 'PO', 'CONTRATO', 'INVOICE', 'REMESA', 'OTRO')),
    nombre_documento NVARCHAR(255) NOT NULL,
    ruta_documento NVARCHAR(500) NOT NULL,
    descripcion NVARCHAR(500),
    fecha_carga DATETIME DEFAULT GETDATE(),
    usuario_carga NVARCHAR(100),
    tamaño_archivo INT,
    tipo_archivo NVARCHAR(20),
    CONSTRAINT FK_Documento_Orden FOREIGN KEY (id_orden) REFERENCES orden(id_orden)
);

-- Tabla: COMUNICACION_CLIENTE - Registro de comunicaciones (NUEVA)
CREATE TABLE comunicacion_cliente (
    id_comunicacion INT PRIMARY KEY IDENTITY(1,1),
    id_cliente INT NOT NULL,
    id_usuario INT,
    id_orden INT,
    tipo_comunicacion NVARCHAR(30) NOT NULL CHECK (tipo_comunicacion IN ('EMAIL', 'LLAMADA', 'MENSAJE', 'REUNION', 'VIDEO_LLAMADA')),
    asunto NVARCHAR(200),
    contenido NVARCHAR(MAX),
    archivo_adjunto NVARCHAR(500),
    ruta_archivo NVARCHAR(500),
    fecha_comunicacion DATETIME DEFAULT GETDATE(),
    estado_resuelto BIT DEFAULT 0,
    fecha_resolucion DATETIME,
    CONSTRAINT FK_Comunicacion_Cliente FOREIGN KEY (id_cliente) REFERENCES cliente(id_cliente),
    CONSTRAINT FK_Comunicacion_Usuario FOREIGN KEY (id_usuario) REFERENCES usuario(id_usuario),
    CONSTRAINT FK_Comunicacion_Orden FOREIGN KEY (id_orden) REFERENCES orden(id_orden)
);

-- =====================================================================================================================
-- FASE 4: TABLA ESPECIAL - AUDITORÍA
-- =====================================================================================================================

-- Tabla: BITÁCORA - Registro de todos los cambios en la base de datos
CREATE TABLE bitacora (
    id_bitacora INT PRIMARY KEY IDENTITY(1,1),
    tabla NVARCHAR(100) NOT NULL,
    campo NVARCHAR(100),
    llave_primaria NVARCHAR(100),
    valor_anterior NVARCHAR(500),
    valor_nuevo NVARCHAR(500),
    fecha_creacion DATETIME DEFAULT GETDATE(),
    usuario_creacion NVARCHAR(100),
    tipo_movimiento NVARCHAR(40) NOT NULL CHECK (tipo_movimiento IN ('CREACION', 'MODIFICACION', 'ELIMINACION')),
    ip_registro NVARCHAR(50)
);


-- =====================================================================================================================
-- PROCEDIMIENTOS DE INSERCIÓN QUE VERIFICAN DUPLICADOS EN LLAVES PRIMARIAS
-- =====================================================================================================================

-- 1. Inserción segura para GIRO_NEGOCIO
CREATE OR ALTER PROCEDURE sp_InsertarGiroNegocioSeguro
    @codigo_giro NVARCHAR(10),
    @nombre_giro NVARCHAR(100),
    @tipo_giro NVARCHAR(50),
    @id_giro_negocio_output INT OUTPUT
AS
BEGIN
    SET NOCOUNT ON;
    
    BEGIN TRY
        BEGIN TRANSACTION;
        
        -- Insertar normalmente (ID es IDENTITY, no puede duplicarse)
        INSERT INTO giro_negocio (codigo_giro, nombre_giro, tipo_giro)
        VALUES (@codigo_giro, @nombre_giro, @tipo_giro);
        
        -- Obtener el ID generado
        SET @id_giro_negocio_output = SCOPE_IDENTITY();
        
        COMMIT TRANSACTION;
        
        SELECT 
            'Inserción exitosa' AS mensaje,
            @id_giro_negocio_output AS id_generado;
            
    END TRY
    BEGIN CATCH
        IF @@TRANCOUNT > 0
            ROLLBACK TRANSACTION;
        
        SELECT 
            'Error: ' + ERROR_MESSAGE() AS mensaje,
            -1 AS id_generado;
    END CATCH
END;
GO

-- 2. Inserción segura para PERSONA
CREATE OR ALTER PROCEDURE sp_InsertarPersonaSegura
    @tipo_persona NVARCHAR(20),
    @numero_documento NVARCHAR(20),
    @id_persona_output INT OUTPUT
AS
BEGIN
    SET NOCOUNT ON;
    
    BEGIN TRY
        BEGIN TRANSACTION;
        
        -- Verificar duplicado por número de documento (campo único)
        IF EXISTS (SELECT 1 FROM persona WHERE numero_documento = @numero_documento)
        BEGIN
            RAISERROR('Ya existe una persona con este número de documento.', 16, 1);
            ROLLBACK TRANSACTION;
            RETURN -1;
        END
        
        -- Insertar persona
        INSERT INTO persona (tipo_persona, numero_documento)
        VALUES (@tipo_persona, @numero_documento);
        
        -- Obtener ID generado
        SET @id_persona_output = SCOPE_IDENTITY();
        
        COMMIT TRANSACTION;
        
        SELECT 
            'Persona insertada correctamente' AS mensaje,
            @id_persona_output AS id_generado;
            
    END TRY
    BEGIN CATCH
        IF @@TRANCOUNT > 0
            ROLLBACK TRANSACTION;
        
        SELECT 
            'Error: ' + ERROR_MESSAGE() AS mensaje,
            -1 AS id_generado;
    END CATCH
END;
GO

-- 3. Inserción segura para EMPLEADO
CREATE OR ALTER PROCEDURE sp_InsertarEmpleadoSeguro
    @id_persona INT,
    @id_sucursal INT,
    @codigo_empleado NVARCHAR(20),
    @fecha_ingreso DATE,
    @id_empleado_output INT OUTPUT
AS
BEGIN
    SET NOCOUNT ON;
    
    BEGIN TRY
        BEGIN TRANSACTION;
        
        -- Verificar que la persona existe
        IF NOT EXISTS (SELECT 1 FROM persona WHERE id_persona = @id_persona)
        BEGIN
            RAISERROR('La persona especificada no existe.', 16, 1);
            ROLLBACK TRANSACTION;
            RETURN -1;
        END
        
        -- Verificar duplicado por código_empleado
        IF EXISTS (SELECT 1 FROM empleado WHERE codigo_empleado = @codigo_empleado)
        BEGIN
            RAISERROR('Ya existe un empleado con este código.', 16, 1);
            ROLLBACK TRANSACTION;
            RETURN -1;
        END
        
        -- Insertar empleado
        INSERT INTO empleado (id_persona, id_sucursal, id_estado_empleado, codigo_empleado, fecha_ingreso)
        VALUES (@id_persona, @id_sucursal, 1, @codigo_empleado, @fecha_ingreso); -- Estado 1 = ACTIVO
        
        -- Obtener ID generado
        SET @id_empleado_output = SCOPE_IDENTITY();
        
        COMMIT TRANSACTION;
        
        SELECT 
            'Empleado insertado correctamente' AS mensaje,
            @id_empleado_output AS id_generado;
            
    END TRY
    BEGIN CATCH
        IF @@TRANCOUNT > 0
            ROLLBACK TRANSACTION;
        
        SELECT 
            'Error: ' + ERROR_MESSAGE() AS mensaje,
            -1 AS id_generado;
    END CATCH
END;
GO

-- 4. Inserción segura para ORDEN
CREATE OR ALTER PROCEDURE sp_InsertarOrdenSegura
    @tipo_orden NVARCHAR(30),
    @numero_orden NVARCHAR(50),
    @id_estado_orden INT,
    @id_orden_output INT OUTPUT
AS
BEGIN
    SET NOCOUNT ON;
    
    BEGIN TRY
        BEGIN TRANSACTION;
        
        -- Verificar duplicado por número_orden
        IF EXISTS (SELECT 1 FROM orden WHERE numero_orden = @numero_orden)
        BEGIN
            RAISERROR('Ya existe una orden con este número.', 16, 1);
            ROLLBACK TRANSACTION;
            RETURN -1;
        END
        
        -- Insertar orden
        INSERT INTO orden (tipo_orden, numero_orden, id_estado_orden)
        VALUES (@tipo_orden, @numero_orden, @id_estado_orden);
        
        -- Obtener ID generado
        SET @id_orden_output = SCOPE_IDENTITY();
        
        COMMIT TRANSACTION;
        
        SELECT 
            'Orden insertada correctamente' AS mensaje,
            @id_orden_output AS id_generado;
            
    END TRY
    BEGIN CATCH
        IF @@TRANCOUNT > 0
            ROLLBACK TRANSACTION;
        
        SELECT 
            'Error: ' + ERROR_MESSAGE() AS mensaje,
            -1 AS id_generado;
    END CATCH
END;
GO

-- =====================================================================================================================
-- FASE 5: INSERCIÓN DE DATOS MAESTROS INICIALES
-- =====================================================================================================================

-- Insertar giros de negocio
INSERT INTO giro_negocio (codigo_giro, nombre_giro, tipo_giro, descripcion) VALUES
('EXT', 'Extracción Minera', 'EXTRACCION', 'Plantas de extracción minera en Cobán, El Progreso, Quetzaltenango y Petén'),
('PRO', 'Proceso Materia Prima', 'PROCESO', 'Plantas de proceso para materiales de construcción en Zacapa, Huehuetenango y Petén'),
('MAQ', 'Venta y Alquiler Maquinaria', 'VENTA_MAQUINARIA', 'Venta y alquiler de maquinaria de construcción en Guatemala, Xela, Zacapa y Petén'),
('TRANS', 'Servicio de Transporte', 'TRANSPORTE', 'Transporte de materia prima y productos para empresas nacionales e internacionales'),
('CONST', 'Servicios de Construcción', 'CONSTRUCCION', 'Construcción de puentes, carreteras, edificios y obras civiles');

-- Insertar estados de empleado
INSERT INTO estado_empleado (estado, descripcion) VALUES
('ACTIVO', 'Empleado activo en la empresa'),
('INACTIVO', 'Empleado inactivo sin labores'),
('VACACIONES', 'Empleado en período de vacaciones'),
('LICENCIA', 'Empleado en licencia con o sin goce de sueldo'),
('INCAPACIDAD', 'Empleado con incapacidad médica'),
('SUSPENDIDO', 'Empleado suspendido temporalmente');

-- Insertar estados de orden
INSERT INTO estado_orden (estado, descripcion, es_final) VALUES
('PENDIENTE', 'Orden pendiente de procesar', 0),
('EN_PROCESO', 'Orden en proceso de atención', 0),
('COMPLETADA', 'Orden completada exitosamente', 1),
('CANCELADA', 'Orden cancelada', 1),
('PARCIAL', 'Orden parcialmente completada', 0),
('RECHAZADA', 'Orden rechazada', 1);

-- Insertar tipos de alerta
INSERT INTO tipo_alerta (nombre_alerta, descripcion, prioridad) VALUES
('STOCK_BAJO', 'Stock por debajo del mínimo establecido', 'ALTA'),
('GPS_INACTIVO', 'Dispositivo GPS sin reportar ubicación', 'MEDIA'),
('ENTREGA_ATRASADA', 'Entrega fuera del tiempo estimado', 'ALTA'),
('MANTENIMIENTO', 'Equipo requiere mantenimiento preventivo', 'MEDIA'),
('PEDIDO_PENDIENTE', 'Pedido interno pendiente de atención', 'BAJA'),
('VELOCIDAD_EXCESIVA', 'Vehículo circula a velocidad sobre el límite', 'ALTA'),
('TEMPERATURA_ANORMAL', 'Temperatura fuera de rango en transporte', 'ALTA'),
('COMBUSTIBLE_BAJO', 'Nivel de combustible crítico', 'MEDIA'),
('COMUNICACION_CLIENTE', 'Nuevo mensaje de cliente', 'MEDIA'),
('USUARIO_CREADO', 'Nuevo usuario creado en el sistema', 'BAJA');

-- Insertar métodos de pago
INSERT INTO metodo_pago (nombre_metodo, descripcion) VALUES
('EFECTIVO', 'Pago en efectivo'),
('TARJETA_CREDITO', 'Pago con tarjeta de crédito'),
('TARJETA_DEBITO', 'Pago con tarjeta de débito'),
('TRANSFERENCIA', 'Transferencia bancaria'),
('CHEQUE', 'Pago con cheque'),
('DEPOSITO', 'Depósito bancario'),
('LINEA_CREDITO', 'Línea de crédito convenida');

-- Insertar SUCURSALES por giro de negocio
INSERT INTO sucursal (id_giro_negocio, codigo_sucursal, nombre_sucursal, departamento, direccion, telefono, fecha_apertura) VALUES
-- Sucursales para EXTRACCIÓN MINERA
(1, 'EXT-CBN', 'Planta Cobán', 'Alta Verapaz', 'Kilómetro 12 Carretera a Chisec, Cobán', '7951-2345', '2020-01-15'),
(1, 'EXT-PRO', 'Planta El Progreso', 'El Progreso', 'Zona Industrial, El Progreso', '7942-5678', '2019-03-20'),
(1, 'EXT-XELA', 'Planta Quetzaltenango', 'Quetzaltenango', 'Carretera a Olintepeque, Quetzaltenango', '7761-8901', '2021-06-10'),
(1, 'EXT-PTN', 'Planta Petén', 'Petén', 'Ruta a Flores, Santa Elena, Petén', '7865-4321', '2018-11-05'),

-- Sucursales para PROCESO MATERIA PRIMA
(2, 'PRO-ZAC', 'Planta Zacapa', 'Zacapa', 'Parque Industrial, Zacapa', '7941-2345', '2019-08-12'),
(2, 'PRO-HUE', 'Planta Huehuetenango', 'Huehuetenango', 'Zona 5, Huehuetenango', '7765-6789', '2020-02-28'),
(2, 'PRO-PTN', 'Planta Petén Proceso', 'Petén', 'Carretera a Sayaxché, Petén', '7868-9012', '2021-04-15'),

-- Sucursales para VENTA Y ALQUILER MAQUINARIA
(3, 'MAQ-GUA', 'Sucursal Guatemala', 'Guatemala', 'Calzada Roosevelt 25-50, Zona 11', '2201-2345', '2017-05-20'),
(3, 'MAQ-XELA', 'Sucursal Xela', 'Quetzaltenango', '15 Avenida 5-67, Zona 3, Quetzaltenango', '7762-3456', '2018-09-10'),
(3, 'MAQ-ZAC', 'Sucursal Zacapa', 'Zacapa', '4ta Calle 2-45, Zona 1, Zacapa', '7943-4567', '2019-11-30'),
(3, 'MAQ-PTN', 'Sucursal Petén', 'Petén', 'Avenida Santa Elena, Flores, Petén', '7869-5678', '2020-07-22'),

-- Sucursales para SERVICIO TRANSPORTE
(4, 'TRANS-CEN', 'Centro Logístico', 'Guatemala', 'Kilómetro 18.5 Carretera a El Salvador', '2202-3456', '2016-12-01'),
(4, 'TRANS-OCC', 'Base Occidente', 'Quetzaltenango', 'Carretera a San Marcos, Quetzaltenango', '7763-4567', '2017-08-15'),
(4, 'TRANS-ORI', 'Base Oriente', 'Zacapa', 'Zona Industrial, Zacapa', '7944-5678', '2018-04-10'),

-- Sucursales para CONSTRUCCIÓN
(5, 'CONST-CEN', 'Oficina Central Construcción', 'Guatemala', 'Edificio Sixtino, Zona 10', '2203-4567', '2015-10-05'),
(5, 'CONST-PROY', 'Gerencia de Proyectos', 'Guatemala', 'Edificio Sixtino, Zona 10', '2203-4568', '2015-10-05');

-- Insertar CATEGORÍAS DE INVENTARIO por giro de negocio
INSERT INTO categoria_inventario (id_giro_negocio, nombre_categoria, descripcion) VALUES
-- Categorías para EXTRACCIÓN MINERA
(1, 'Minerales Base', 'Minerales extraídos en estado natural'),
(1, 'Material Triturado', 'Material procesado primario'),
(1, 'Equipo Extracción', 'Maquinaria para extracción minera'),
(1, 'Insumos Minería', 'Insumos y materiales para operación minera'),

-- Categorías para PROCESO MATERIA PRIMA
(2, 'Materias Primas', 'Materiales base para proceso'),
(2, 'Pisos Cerámicos', 'Pisos cerámicos de diferentes tipos'),
(2, 'Materiales Construcción', 'Materiales de construcción terminados'),
(2, 'Productos Terminados', 'Productos finales de planta'),

-- Categorías para VENTA Y ALQUILER MAQUINARIA
(3, 'Excavadoras', 'Excavadoras de diferentes capacidades'),
(3, 'Cargadores', 'Cargadores frontales y laterales'),
(3, 'Compactadores', 'Equipo para compactación de suelos'),
(3, 'Grúas', 'Grúas móviles y torre'),
(3, 'Generadores', 'Plantas generadoras de energía'),
(3, 'Equipo Menor', 'Equipo menor de construcción'),

-- Categorías para SERVICIO TRANSPORTE
(4, 'Camiones Volquete', 'Camiones para transporte de materiales'),
(4, 'Camiones Planos', 'Camiones de plataforma plana'),
(4, 'Camiones Cisterna', 'Camiones para líquidos'),
(4, 'Trailers', 'Trailers de diferentes capacidades'),
(4, 'Repuestos Vehiculares', 'Repuestos y accesorios para vehículos'),

-- Categorías para CONSTRUCCIÓN
(5, 'Materiales Obra', 'Materiales directos de construcción'),
(5, 'Equipo Construcción', 'Equipo especializado para construcción'),
(5, 'Herramientas', 'Herramientas manuales y eléctricas'),
(5, 'EPP', 'Equipo de protección personal');

-- Insertar PERSONAS de ejemplo (Clientes, Empleados, Proveedores)
INSERT INTO persona (tipo_persona, primer_nombre, primer_apellido, razon_social, numero_documento, nit, telefono, email, direccion, departamento) VALUES
-- Personas naturales (empleados)
('NATURAL', 'Juan', 'Pérez', NULL, '1234567890101', '123456-7', '5555-1234', 'juan.perez@transport.com', '15 Avenida 5-25 Zona 1', 'Guatemala'),
('NATURAL', 'María', 'López', NULL, '1234567890102', '123457-7', '5555-5678', 'maria.lopez@transport.com', '8va Calle 3-45 Zona 4', 'Guatemala'),
('NATURAL', 'Carlos', 'García', NULL, '1234567890103', '123458-7', '5555-9012', 'carlos.garcia@transport.com', '12 Calle 2-67 Zona 5', 'Guatemala'),
('NATURAL', 'Ana', 'Martínez', NULL, '1234567890104', '123459-7', '5555-3456', 'ana.martinez@transport.com', '20 Avenida 8-90 Zona 10', 'Guatemala'),

-- Personas jurídicas (clientes corporativos)
('JURIDICA', NULL, NULL, 'Cementos Progreso S.A.', '1234567890105', '123460-7', '2201-1000', 'compras@cempro.com', 'Planta San Miguel, Guatemala', 'Guatemala'),
('JURIDICA', NULL, NULL, 'CEMEX Guatemala', '1234567890106', '123461-7', '2201-2000', 'operaciones@cemex.com.gt', 'Carretera a El Salvador, Km 15', 'Guatemala'),
('JURIDICA', NULL, NULL, 'Hispacensa S.A.', '1234567890107', '123462-7', '2201-3000', 'logistica@hispacensa.com', 'Zona Industrial, Villa Nueva', 'Guatemala'),
('JURIDICA', NULL, NULL, 'Constructora Nacional S.A.', '1234567890108', '123463-7', '2201-4000', 'proyectos@constructora.com', 'Edificio Torre Azul, Zona 10', 'Guatemala'),

-- Proveedores
('JURIDICA', NULL, NULL, 'Distribuidora de Combustibles S.A.', '1234567890109', '123464-7', '2201-5000', 'ventas@discomsa.com', 'Calzada Roosevelt 30-50', 'Guatemala'),
('JURIDICA', NULL, NULL, 'Repuestos Industriales S.A.', '1234567890110', '123465-7', '2201-6000', 'pedidos@repuestos.com', 'Zona 12, Guatemala', 'Guatemala');

-- Insertar CLIENTES
INSERT INTO cliente (id_persona, categoria, limite_credito) VALUES
(5, 'VIP', 500000.00),  -- Cementos Progreso
(6, 'PREMIUM', 300000.00),  -- CEMEX
(7, 'REGULAR', 200000.00),  -- Hispacensa
(8, 'PREMIUM', 400000.00);  -- Constructora Nacional

-- Insertar PROVEEDORES
INSERT INTO proveedor (id_persona, nombre_comercial, categoria_proveedor, calificacion, dias_plazo, contacto_principal) VALUES
(9, 'DISCOM', 'COMBUSTIBLES', 4.5, 30, 'Ing. Roberto Fuentes'),
(10, 'REPISA', 'REPUESTOS', 4.2, 45, 'Lic. Sandra Morales');

-- Insertar EMPLEADOS
INSERT INTO empleado (id_persona, id_sucursal, id_estado_empleado, codigo_empleado, fecha_ingreso, salario_base, cargo, departamento) VALUES
(1, 13, 1, 'EMP-001', '2020-01-15', 15000.00, 'Gerente General', 'Gerencia'),
(2, 1, 1, 'EMP-002', '2021-03-20', 12000.00, 'Supervisor de Planta', 'Producción'),
(3, 9, 1, 'EMP-003', '2019-06-10', 10000.00, 'Vendedor Senior', 'Ventas'),
(4, 12, 1, 'EMP-004', '2022-01-08', 8000.00, 'Operador Logístico', 'Logística');

-- Insertar USUARIOS del sistema
INSERT INTO usuario (id_empleado, nombre_usuario, password_hash, email, id_giro_negocio, rol) VALUES (1, 'melody', 'adminhash123', 'admin.melody@transport.com', NULL, 'ADMIN');


-- =====================================================================================================================
-- FASE 6: CREAR ÍNDICES PARA PERFORMANCE
-- =====================================================================================================================

-- Índices en tablas maestras
CREATE INDEX idx_usuario_nombre ON usuario(nombre_usuario);
CREATE INDEX idx_usuario_empleado ON usuario(id_empleado);
CREATE INDEX idx_usuario_giro ON usuario(id_giro_negocio);
CREATE INDEX idx_usuario_activo ON usuario(activo);

CREATE INDEX idx_empleado_persona ON empleado(id_persona);
CREATE INDEX idx_empleado_sucursal ON empleado(id_sucursal);
CREATE INDEX idx_empleado_codigo ON empleado(codigo_empleado);
CREATE INDEX idx_empleado_activo ON empleado(activo);

CREATE INDEX idx_cliente_persona ON cliente(id_persona);
CREATE INDEX idx_cliente_activo ON cliente(activo);

CREATE INDEX idx_proveedor_persona ON proveedor(id_persona);
CREATE INDEX idx_proveedor_activo ON proveedor(activo);

CREATE INDEX idx_sucursal_giro ON sucursal(id_giro_negocio);
CREATE INDEX idx_sucursal_codigo ON sucursal(codigo_sucursal);
CREATE INDEX idx_sucursal_activa ON sucursal(activa);

-- Índices en tablas operativas (CRÍTICOS)
CREATE INDEX idx_inventario_codigo ON inventario(codigo_producto);
CREATE INDEX idx_inventario_sucursal ON inventario(id_sucursal);
CREATE INDEX idx_inventario_categoria ON inventario(id_categoria);
CREATE INDEX idx_inventario_minimos ON inventario(existencia_actual, existencia_minima);
CREATE INDEX idx_inventario_activo ON inventario(activo);
CREATE INDEX idx_inventario_barras ON inventario(codigo_barras);

CREATE INDEX idx_orden_numero ON orden(numero_orden);
CREATE INDEX idx_orden_estado ON orden(id_estado_orden);
CREATE INDEX idx_orden_giro_origen ON orden(id_giro_negocio_origen);
CREATE INDEX idx_orden_giro_destino ON orden(id_giro_negocio_destino);
CREATE INDEX idx_orden_giro_ejecutor ON orden(id_giro_negocio_ejecutor);
CREATE INDEX idx_orden_cliente ON orden(id_cliente);
CREATE INDEX idx_orden_proveedor ON orden(id_proveedor);
CREATE INDEX idx_orden_empleado ON orden(id_empleado_responsable);
CREATE INDEX idx_orden_tipo ON orden(tipo_orden);
CREATE INDEX idx_orden_fecha ON orden(fecha_creacion);

CREATE INDEX idx_detalle_orden ON detalle_orden(id_orden);
CREATE INDEX idx_detalle_inventario ON detalle_orden(id_inventario);
CREATE INDEX idx_detalle_estado ON detalle_orden(estado_detalle);

CREATE INDEX idx_remision_orden ON remision(id_orden);
CREATE INDEX idx_remision_dispositivo ON remision(id_dispositivo);
CREATE INDEX idx_remision_numero ON remision(numero_remision);
CREATE INDEX idx_remision_estado ON remision(estado);
CREATE INDEX idx_remision_fecha ON remision(fecha_emision);

-- Índices GPS
CREATE INDEX idx_dispositivo_inventario ON dispositivo_gps(id_inventario);
CREATE INDEX idx_dispositivo_placa ON dispositivo_gps(placa_vehiculo);
CREATE INDEX idx_dispositivo_codigo ON dispositivo_gps(codigo_dispositivo);

CREATE INDEX idx_monitoreo_dispositivo ON monitoreo_gps(id_dispositivo);
CREATE INDEX idx_monitoreo_fecha ON monitoreo_gps(fecha_hora);
CREATE INDEX idx_monitoreo_velocidad ON monitoreo_gps(velocidad);

-- Índices de alertas y comunicaciones
CREATE INDEX idx_alerta_usuario ON alerta_sistema(id_usuario_destino);
CREATE INDEX idx_alerta_giro ON alerta_sistema(id_giro_negocio);
CREATE INDEX idx_alerta_tipo ON alerta_sistema(id_tipo_alerta);
CREATE INDEX idx_alerta_leida ON alerta_sistema(leida);
CREATE INDEX idx_alerta_fecha ON alerta_sistema(fecha_creacion);

CREATE INDEX idx_comunicacion_cliente ON comunicacion_cliente(id_cliente);
CREATE INDEX idx_comunicacion_orden ON comunicacion_cliente(id_orden);
CREATE INDEX idx_comunicacion_fecha ON comunicacion_cliente(fecha_comunicacion);
CREATE INDEX idx_comunicacion_resuelto ON comunicacion_cliente(estado_resuelto);

-- Índices de facturación y movimientos
CREATE INDEX idx_factura_orden ON factura(id_orden);
CREATE INDEX idx_factura_numero ON factura(numero_factura);
CREATE INDEX idx_factura_estado ON factura(estado);
CREATE INDEX idx_factura_fecha ON factura(fecha_emision);

CREATE INDEX idx_movimiento_inventario ON movimiento_inventario(id_inventario);
CREATE INDEX idx_movimiento_orden ON movimiento_inventario(id_orden);
CREATE INDEX idx_movimiento_tipo ON movimiento_inventario(tipo_movimiento);
CREATE INDEX idx_movimiento_fecha ON movimiento_inventario(fecha_movimiento);

CREATE INDEX idx_documento_orden ON documento_asociado(id_orden);
CREATE INDEX idx_documento_tipo ON documento_asociado(tipo_documento);

-- Índices en bitácora
CREATE INDEX idx_bitacora_tabla ON bitacora(tabla, fecha_creacion);
CREATE INDEX idx_bitacora_tipo ON bitacora(tipo_movimiento);
CREATE INDEX idx_bitacora_usuario ON bitacora(usuario_creacion);

-- Índices compuestos para búsquedas frecuentes
CREATE INDEX idx_orden_giro_estado ON orden(id_giro_negocio_origen, id_estado_orden);
CREATE INDEX idx_orden_giro_fecha ON orden(id_giro_negocio_origen, fecha_creacion);
CREATE INDEX idx_inventario_sucursal_stock ON inventario(id_sucursal, existencia_actual);
CREATE INDEX idx_inventario_categoria_activo ON inventario(id_categoria, activo);
CREATE INDEX idx_monitoreo_dispositivo_fecha ON monitoreo_gps(id_dispositivo, fecha_hora);

-- =====================================================================================================================
-- FASE 7: CREAR TRIGGERS PARA BITÁCORA - TODAS LAS TABLAS
-- =====================================================================================================================


-- 1. TRIGGER CORREGIDO PARA GIRO_NEGOCIO
CREATE OR ALTER TRIGGER TR_GiroNegocio_Bitacora
ON giro_negocio
AFTER INSERT, UPDATE, DELETE
AS
BEGIN
    SET NOCOUNT ON;
    
    DECLARE @usuario NVARCHAR(100) = SYSTEM_USER;
    DECLARE @ip NVARCHAR(50) = (SELECT client_net_address FROM sys.dm_exec_connections WHERE session_id = @@SPID);
    
    -- INSERT
    IF EXISTS (SELECT * FROM inserted) AND NOT EXISTS (SELECT * FROM deleted)
    BEGIN
        INSERT INTO bitacora (tabla, campo, llave_primaria, valor_anterior, valor_nuevo, fecha_creacion, usuario_creacion, tipo_movimiento, ip_registro)
        SELECT 
            'giro_negocio',
            'id_giro_negocio',
            CAST(i.id_giro_negocio AS NVARCHAR(100)),
            NULL,
            CAST(i.id_giro_negocio AS NVARCHAR(100)),
            GETDATE(),
            @usuario,
            'CREACION',
            @ip
        FROM inserted i;
    END
    
    -- UPDATE
    IF EXISTS (SELECT * FROM inserted) AND EXISTS (SELECT * FROM deleted)
    BEGIN
        INSERT INTO bitacora (tabla, campo, llave_primaria, valor_anterior, valor_nuevo, fecha_creacion, usuario_creacion, tipo_movimiento, ip_registro)
        SELECT 
            'giro_negocio',
            'nombre_giro',
            CAST(i.id_giro_negocio AS NVARCHAR(100)),
            d.nombre_giro,
            i.nombre_giro,
            GETDATE(),
            @usuario,
            'MODIFICACION',
            @ip
        FROM inserted i
        INNER JOIN deleted d ON i.id_giro_negocio = d.id_giro_negocio
        WHERE i.nombre_giro != d.nombre_giro OR (i.nombre_giro IS NULL AND d.nombre_giro IS NOT NULL) OR (i.nombre_giro IS NOT NULL AND d.nombre_giro IS NULL);
    END
    
    -- DELETE
    IF EXISTS (SELECT * FROM deleted) AND NOT EXISTS (SELECT * FROM inserted)
    BEGIN
        INSERT INTO bitacora (tabla, campo, llave_primaria, valor_anterior, valor_nuevo, fecha_creacion, usuario_creacion, tipo_movimiento, ip_registro)
        SELECT 
            'giro_negocio',
            'id_giro_negocio',
            CAST(d.id_giro_negocio AS NVARCHAR(100)),
            CAST(d.id_giro_negocio AS NVARCHAR(100)),
            NULL,
            GETDATE(),
            @usuario,
            'ELIMINACION',
            @ip
        FROM deleted d;
    END
END;
GO

-- 2. TRIGGER CORREGIDO PARA PERSONA
CREATE OR ALTER TRIGGER TR_Persona_Bitacora
ON persona
AFTER INSERT, UPDATE, DELETE
AS
BEGIN
    SET NOCOUNT ON;
    
    DECLARE @usuario NVARCHAR(100) = SYSTEM_USER;
    DECLARE @ip NVARCHAR(50) = (SELECT client_net_address FROM sys.dm_exec_connections WHERE session_id = @@SPID);
    
    -- INSERT
    IF EXISTS (SELECT * FROM inserted) AND NOT EXISTS (SELECT * FROM deleted)
    BEGIN
        INSERT INTO bitacora (tabla, campo, llave_primaria, valor_anterior, valor_nuevo, fecha_creacion, usuario_creacion, tipo_movimiento, ip_registro)
        SELECT 
            'persona',
            'id_persona',
            CAST(i.id_persona AS NVARCHAR(100)),
            NULL,
            CAST(i.id_persona AS NVARCHAR(100)),
            GETDATE(),
            @usuario,
            'CREACION',
            @ip
        FROM inserted i;
    END
    
    -- UPDATE - Campos principales
    IF EXISTS (SELECT * FROM inserted) AND EXISTS (SELECT * FROM deleted)
    BEGIN
        -- Cambios en nombre
        INSERT INTO bitacora (tabla, campo, llave_primaria, valor_anterior, valor_nuevo, fecha_creacion, usuario_creacion, tipo_movimiento, ip_registro)
        SELECT 
            'persona',
            'primer_nombre',
            CAST(i.id_persona AS NVARCHAR(100)),
            d.primer_nombre,
            i.primer_nombre,
            GETDATE(),
            @usuario,
            'MODIFICACION',
            @ip
        FROM inserted i
        INNER JOIN deleted d ON i.id_persona = d.id_persona
        WHERE i.primer_nombre != d.primer_nombre OR (i.primer_nombre IS NULL AND d.primer_nombre IS NOT NULL) OR (i.primer_nombre IS NOT NULL AND d.primer_nombre IS NULL);
        
        -- Cambios en email
        INSERT INTO bitacora (tabla, campo, llave_primaria, valor_anterior, valor_nuevo, fecha_creacion, usuario_creacion, tipo_movimiento, ip_registro)
        SELECT 
            'persona',
            'email',
            CAST(i.id_persona AS NVARCHAR(100)),
            d.email,
            i.email,
            GETDATE(),
            @usuario,
            'MODIFICACION',
            @ip
        FROM inserted i
        INNER JOIN deleted d ON i.id_persona = d.id_persona
        WHERE i.email != d.email OR (i.email IS NULL AND d.email IS NOT NULL) OR (i.email IS NOT NULL AND d.email IS NULL);
    END
    
    -- DELETE
    IF EXISTS (SELECT * FROM deleted) AND NOT EXISTS (SELECT * FROM inserted)
    BEGIN
        INSERT INTO bitacora (tabla, campo, llave_primaria, valor_anterior, valor_nuevo, fecha_creacion, usuario_creacion, tipo_movimiento, ip_registro)
        SELECT 
            'persona',
            'id_persona',
            CAST(d.id_persona AS NVARCHAR(100)),
            CAST(d.id_persona AS NVARCHAR(100)),
            NULL,
            GETDATE(),
            @usuario,
            'ELIMINACION',
            @ip
        FROM deleted d;
    END
END;
GO

-- 3. TRIGGER CORREGIDO PARA EMPLEADO
CREATE OR ALTER TRIGGER TR_Empleado_Bitacora
ON empleado
AFTER INSERT, UPDATE, DELETE
AS
BEGIN
    SET NOCOUNT ON;
    
    DECLARE @usuario NVARCHAR(100) = SYSTEM_USER;
    DECLARE @ip NVARCHAR(50) = (SELECT client_net_address FROM sys.dm_exec_connections WHERE session_id = @@SPID);
    
    -- INSERT
    IF EXISTS (SELECT * FROM inserted) AND NOT EXISTS (SELECT * FROM deleted)
    BEGIN
        INSERT INTO bitacora (tabla, campo, llave_primaria, valor_anterior, valor_nuevo, fecha_creacion, usuario_creacion, tipo_movimiento, ip_registro)
        SELECT 
            'empleado',
            'id_empleado',
            CAST(i.id_empleado AS NVARCHAR(100)),
            NULL,
            CAST(i.id_empleado AS NVARCHAR(100)),
            GETDATE(),
            @usuario,
            'CREACION',
            @ip
        FROM inserted i;
    END
    
    -- UPDATE
    IF EXISTS (SELECT * FROM inserted) AND EXISTS (SELECT * FROM deleted)
    BEGIN
        -- Cambios en salario
        INSERT INTO bitacora (tabla, campo, llave_primaria, valor_anterior, valor_nuevo, fecha_creacion, usuario_creacion, tipo_movimiento, ip_registro)
        SELECT 
            'empleado',
            'salario_base',
            CAST(i.id_empleado AS NVARCHAR(100)),
            CAST(d.salario_base AS NVARCHAR(100)),
            CAST(i.salario_base AS NVARCHAR(100)),
            GETDATE(),
            @usuario,
            'MODIFICACION',
            @ip
        FROM inserted i
        INNER JOIN deleted d ON i.id_empleado = d.id_empleado
        WHERE i.salario_base != d.salario_base;
        
        -- Cambios en estado
        INSERT INTO bitacora (tabla, campo, llave_primaria, valor_anterior, valor_nuevo, fecha_creacion, usuario_creacion, tipo_movimiento, ip_registro)
        SELECT 
            'empleado',
            'activo',
            CAST(i.id_empleado AS NVARCHAR(100)),
            CAST(d.activo AS NVARCHAR(100)),
            CAST(i.activo AS NVARCHAR(100)),
            GETDATE(),
            @usuario,
            'MODIFICACION',
            @ip
        FROM inserted i
        INNER JOIN deleted d ON i.id_empleado = d.id_empleado
        WHERE i.activo != d.activo;
    END
    
    -- DELETE
    IF EXISTS (SELECT * FROM deleted) AND NOT EXISTS (SELECT * FROM inserted)
    BEGIN
        INSERT INTO bitacora (tabla, campo, llave_primaria, valor_anterior, valor_nuevo, fecha_creacion, usuario_creacion, tipo_movimiento, ip_registro)
        SELECT 
            'empleado',
            'id_empleado',
            CAST(d.id_empleado AS NVARCHAR(100)),
            CAST(d.id_empleado AS NVARCHAR(100)),
            NULL,
            GETDATE(),
            @usuario,
            'ELIMINACION',
            @ip
        FROM deleted d;
    END
END;
GO

-- 4. TRIGGER CORREGIDO PARA USUARIO
CREATE OR ALTER TRIGGER TR_Usuario_Bitacora
ON usuario
AFTER INSERT, UPDATE, DELETE
AS
BEGIN
    SET NOCOUNT ON;
    
    DECLARE @usuario NVARCHAR(100) = SYSTEM_USER;
    DECLARE @ip NVARCHAR(50) = (SELECT client_net_address FROM sys.dm_exec_connections WHERE session_id = @@SPID);
    
    -- INSERT
    IF EXISTS (SELECT * FROM inserted) AND NOT EXISTS (SELECT * FROM deleted)
    BEGIN
        INSERT INTO bitacora (tabla, campo, llave_primaria, valor_anterior, valor_nuevo, fecha_creacion, usuario_creacion, tipo_movimiento, ip_registro)
        SELECT 
            'usuario',
            'id_usuario',
            CAST(i.id_usuario AS NVARCHAR(100)),
            NULL,
            CAST(i.id_usuario AS NVARCHAR(100)),
            GETDATE(),
            @usuario,
            'CREACION',
            @ip
        FROM inserted i;
    END
    
    -- UPDATE
    IF EXISTS (SELECT * FROM inserted) AND EXISTS (SELECT * FROM deleted)
    BEGIN
        -- Cambios en rol
        INSERT INTO bitacora (tabla, campo, llave_primaria, valor_anterior, valor_nuevo, fecha_creacion, usuario_creacion, tipo_movimiento, ip_registro)
        SELECT 
            'usuario',
            'rol',
            CAST(i.id_usuario AS NVARCHAR(100)),
            d.rol,
            i.rol,
            GETDATE(),
            @usuario,
            'MODIFICACION',
            @ip
        FROM inserted i
        INNER JOIN deleted d ON i.id_usuario = d.id_usuario
        WHERE i.rol != d.rol;
        
        -- Cambios en estado bloqueado
        INSERT INTO bitacora (tabla, campo, llave_primaria, valor_anterior, valor_nuevo, fecha_creacion, usuario_creacion, tipo_movimiento, ip_registro)
        SELECT 
            'usuario',
            'bloqueado',
            CAST(i.id_usuario AS NVARCHAR(100)),
            CAST(d.bloqueado AS NVARCHAR(100)),
            CAST(i.bloqueado AS NVARCHAR(100)),
            GETDATE(),
            @usuario,
            'MODIFICACION',
            @ip
        FROM inserted i
        INNER JOIN deleted d ON i.id_usuario = d.id_usuario
        WHERE i.bloqueado != d.bloqueado;
    END
    
    -- DELETE
    IF EXISTS (SELECT * FROM deleted) AND NOT EXISTS (SELECT * FROM inserted)
    BEGIN
        INSERT INTO bitacora (tabla, campo, llave_primaria, valor_anterior, valor_nuevo, fecha_creacion, usuario_creacion, tipo_movimiento, ip_registro)
        SELECT 
            'usuario',
            'id_usuario',
            CAST(d.id_usuario AS NVARCHAR(100)),
            CAST(d.id_usuario AS NVARCHAR(100)),
            NULL,
            GETDATE(),
            @usuario,
            'ELIMINACION',
            @ip
        FROM deleted d;
    END
END;
GO

-- 5. TRIGGER CORREGIDO PARA ORDEN
CREATE OR ALTER TRIGGER TR_Orden_Bitacora
ON orden
AFTER INSERT, UPDATE, DELETE
AS
BEGIN
    SET NOCOUNT ON;
    
    DECLARE @usuario NVARCHAR(100) = SYSTEM_USER;
    DECLARE @ip NVARCHAR(50) = (SELECT client_net_address FROM sys.dm_exec_connections WHERE session_id = @@SPID);
    
    -- INSERT
    IF EXISTS (SELECT * FROM inserted) AND NOT EXISTS (SELECT * FROM deleted)
    BEGIN
        INSERT INTO bitacora (tabla, campo, llave_primaria, valor_anterior, valor_nuevo, fecha_creacion, usuario_creacion, tipo_movimiento, ip_registro)
        SELECT 
            'orden',
            'id_orden',
            CAST(i.id_orden AS NVARCHAR(100)),
            NULL,
            CAST(i.id_orden AS NVARCHAR(100)),
            GETDATE(),
            @usuario,
            'CREACION',
            @ip
        FROM inserted i;
    END
    
    -- UPDATE
    IF EXISTS (SELECT * FROM inserted) AND EXISTS (SELECT * FROM deleted)
    BEGIN
        -- Cambios en estado
        INSERT INTO bitacora (tabla, campo, llave_primaria, valor_anterior, valor_nuevo, fecha_creacion, usuario_creacion, tipo_movimiento, ip_registro)
        SELECT 
            'orden',
            'id_estado_orden',
            CAST(i.id_orden AS NVARCHAR(100)),
            CAST(d.id_estado_orden AS NVARCHAR(100)),
            CAST(i.id_estado_orden AS NVARCHAR(100)),
            GETDATE(),
            @usuario,
            'MODIFICACION',
            @ip
        FROM inserted i
        INNER JOIN deleted d ON i.id_orden = d.id_orden
        WHERE i.id_estado_orden != d.id_estado_orden;
        
        -- Cambios en total
        INSERT INTO bitacora (tabla, campo, llave_primaria, valor_anterior, valor_nuevo, fecha_creacion, usuario_creacion, tipo_movimiento, ip_registro)
        SELECT 
            'orden',
            'total',
            CAST(i.id_orden AS NVARCHAR(100)),
            CAST(d.total AS NVARCHAR(100)),
            CAST(i.total AS NVARCHAR(100)),
            GETDATE(),
            @usuario,
            'MODIFICACION',
            @ip
        FROM inserted i
        INNER JOIN deleted d ON i.id_orden = d.id_orden
        WHERE i.total != d.total;
    END
    
    -- DELETE
    IF EXISTS (SELECT * FROM deleted) AND NOT EXISTS (SELECT * FROM inserted)
    BEGIN
        INSERT INTO bitacora (tabla, campo, llave_primaria, valor_anterior, valor_nuevo, fecha_creacion, usuario_creacion, tipo_movimiento, ip_registro)
        SELECT 
            'orden',
            'id_orden',
            CAST(d.id_orden AS NVARCHAR(100)),
            CAST(d.id_orden AS NVARCHAR(100)),
            NULL,
            GETDATE(),
            @usuario,
            'ELIMINACION',
            @ip
        FROM deleted d;
    END
END;
GO

-- 6. TRIGGER CORREGIDO PARA INVENTARIO
CREATE OR ALTER TRIGGER TR_Inventario_Bitacora
ON inventario
AFTER INSERT, UPDATE, DELETE
AS
BEGIN
    SET NOCOUNT ON;
    
    DECLARE @usuario NVARCHAR(100) = SYSTEM_USER;
    DECLARE @ip NVARCHAR(50) = (SELECT client_net_address FROM sys.dm_exec_connections WHERE session_id = @@SPID);
    
    -- INSERT
    IF EXISTS (SELECT * FROM inserted) AND NOT EXISTS (SELECT * FROM deleted)
    BEGIN
        INSERT INTO bitacora (tabla, campo, llave_primaria, valor_anterior, valor_nuevo, fecha_creacion, usuario_creacion, tipo_movimiento, ip_registro)
        SELECT 
            'inventario',
            'id_inventario',
            CAST(i.id_inventario AS NVARCHAR(100)),
            NULL,
            CAST(i.id_inventario AS NVARCHAR(100)),
            GETDATE(),
            @usuario,
            'CREACION',
            @ip
        FROM inserted i;
    END
    
    -- UPDATE
    IF EXISTS (SELECT * FROM inserted) AND EXISTS (SELECT * FROM deleted)
    BEGIN
        -- Cambios en existencia
        INSERT INTO bitacora (tabla, campo, llave_primaria, valor_anterior, valor_nuevo, fecha_creacion, usuario_creacion, tipo_movimiento, ip_registro)
        SELECT 
            'inventario',
            'existencia_actual',
            CAST(i.id_inventario AS NVARCHAR(100)),
            CAST(d.existencia_actual AS NVARCHAR(100)),
            CAST(i.existencia_actual AS NVARCHAR(100)),
            GETDATE(),
            @usuario,
            'MODIFICACION',
            @ip
        FROM inserted i
        INNER JOIN deleted d ON i.id_inventario = d.id_inventario
        WHERE i.existencia_actual != d.existencia_actual;
        
        -- Cambios en precio
        INSERT INTO bitacora (tabla, campo, llave_primaria, valor_anterior, valor_nuevo, fecha_creacion, usuario_creacion, tipo_movimiento, ip_registro)
        SELECT 
            'inventario',
            'precio_venta',
            CAST(i.id_inventario AS NVARCHAR(100)),
            CAST(d.precio_venta AS NVARCHAR(100)),
            CAST(i.precio_venta AS NVARCHAR(100)),
            GETDATE(),
            @usuario,
            'MODIFICACION',
            @ip
        FROM inserted i
        INNER JOIN deleted d ON i.id_inventario = d.id_inventario
        WHERE i.precio_venta != d.precio_venta;
    END
    
    -- DELETE
    IF EXISTS (SELECT * FROM deleted) AND NOT EXISTS (SELECT * FROM inserted)
    BEGIN
        INSERT INTO bitacora (tabla, campo, llave_primaria, valor_anterior, valor_nuevo, fecha_creacion, usuario_creacion, tipo_movimiento, ip_registro)
        SELECT 
            'inventario',
            'id_inventario',
            CAST(d.id_inventario AS NVARCHAR(100)),
            CAST(d.id_inventario AS NVARCHAR(100)),
            NULL,
            GETDATE(),
            @usuario,
            'ELIMINACION',
            @ip
        FROM deleted d;
    END
END;
GO

-- 7. TRIGGER CORREGIDO PARA CLIENTE
CREATE OR ALTER TRIGGER TR_Cliente_Bitacora
ON cliente
AFTER INSERT, UPDATE, DELETE
AS
BEGIN
    SET NOCOUNT ON;
    
    DECLARE @usuario NVARCHAR(100) = SYSTEM_USER;
    DECLARE @ip NVARCHAR(50) = (SELECT client_net_address FROM sys.dm_exec_connections WHERE session_id = @@SPID);
    
    IF EXISTS (SELECT * FROM inserted) AND NOT EXISTS (SELECT * FROM deleted)
    BEGIN
        INSERT INTO bitacora (tabla, campo, llave_primaria, valor_anterior, valor_nuevo, fecha_creacion, usuario_creacion, tipo_movimiento, ip_registro)
        SELECT 'cliente', 'id_cliente', CAST(i.id_cliente AS NVARCHAR(100)), NULL, CAST(i.id_cliente AS NVARCHAR(100)), GETDATE(), @usuario, 'CREACION', @ip
        FROM inserted i;
    END
    ELSE IF EXISTS (SELECT * FROM deleted) AND NOT EXISTS (SELECT * FROM inserted)
    BEGIN
        INSERT INTO bitacora (tabla, campo, llave_primaria, valor_anterior, valor_nuevo, fecha_creacion, usuario_creacion, tipo_movimiento, ip_registro)
        SELECT 'cliente', 'id_cliente', CAST(d.id_cliente AS NVARCHAR(100)), CAST(d.id_cliente AS NVARCHAR(100)), NULL, GETDATE(), @usuario, 'ELIMINACION', @ip
        FROM deleted d;
    END
END;
GO

-- 8. TRIGGER CORREGIDO PARA FACTURA
CREATE OR ALTER TRIGGER TR_Factura_Bitacora
ON factura
AFTER INSERT, UPDATE, DELETE
AS
BEGIN
    SET NOCOUNT ON;
    
    DECLARE @usuario NVARCHAR(100) = SYSTEM_USER;
    DECLARE @ip NVARCHAR(50) = (SELECT client_net_address FROM sys.dm_exec_connections WHERE session_id = @@SPID);
    
    IF EXISTS (SELECT * FROM inserted) AND NOT EXISTS (SELECT * FROM deleted)
    BEGIN
        INSERT INTO bitacora (tabla, campo, llave_primaria, valor_anterior, valor_nuevo, fecha_creacion, usuario_creacion, tipo_movimiento, ip_registro)
        SELECT 'factura', 'id_factura', CAST(i.id_factura AS NVARCHAR(100)), NULL, CAST(i.id_factura AS NVARCHAR(100)), GETDATE(), @usuario, 'CREACION', @ip
        FROM inserted i;
    END
    ELSE IF EXISTS (SELECT * FROM deleted) AND NOT EXISTS (SELECT * FROM inserted)
    BEGIN
        INSERT INTO bitacora (tabla, campo, llave_primaria, valor_anterior, valor_nuevo, fecha_creacion, usuario_creacion, tipo_movimiento, ip_registro)
        SELECT 'factura', 'id_factura', CAST(d.id_factura AS NVARCHAR(100)), CAST(d.id_factura AS NVARCHAR(100)), NULL, GETDATE(), @usuario, 'ELIMINACION', @ip
        FROM deleted d;
    END
END;
GO