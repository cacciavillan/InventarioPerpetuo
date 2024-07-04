# Proyecto de Gestión de Órdenes de Compra

Este proyecto es un sistema de gestión de órdenes de compra desarrollado en Java. Proporciona funcionalidades para crear, procesar y gestionar órdenes de compra y sus respectivos ítems.

## Estructura del Proyecto

El proyecto se organiza en varios paquetes y clases, cada uno con responsabilidades específicas:

### Paquetes Principales

1. **model**: Contiene las clases principales del modelo de datos.
2. **model.dto**: Define los Data Transfer Objects (DTO) utilizados para transferir datos entre capas.
3. **model.factory**: Incluye las fábricas para crear instancias de objetos de dominio.
4. **model.repository**: Contiene las clases de repositorio para la persistencia de datos.
5. **model.service**: Define los servicios que encapsulan la lógica de negocio.
6. **model.util**: Proporciona utilidades y gestores de conexiones a la base de datos.

### Clases Principales

#### `PurchaseOrderService.java`

Clase de servicio para procesar y gestionar órdenes de compra. Incluye métodos para:

- **`processPurchaseOrder(PurchaseOrderDTO orderData)`**: Procesa una nueva orden de compra y la guarda en la base de datos, gestionando la transacción y calculando el valor total de la orden.

#### `PurchaseOrder.java`

Modelo que representa una orden de compra. Contiene:

- **Campos**: `id`, `date`, `supplier`, `items`, `totalValue`.
- **Métodos**: Getters y setters para cada campo, y el método `calculateTotalValue()` para calcular el valor total de la orden basada en los ítems.

#### `PurchaseOrderItem.java`

Modelo que representa un ítem dentro de una orden de compra. Contiene:

- **Campos**: `id`, `type`, `product`, `orderId`, `quantity`, `price`, `totalPrice`.
- **Métodos**: Getters y setters para cada campo, y el método `calculateTotalPrice()` para calcular el precio total del ítem.

#### `RawMaterial.java`

Modelo que representa una materia prima, extendiendo de `AbstractProduct`.

#### `AbstractProduct.java`

Clase abstracta que define propiedades comunes para productos. Contiene:

- **Campos**: `id`, `name`, `uom`, `stock`, `minimumStock`.

#### `ConnManager.java`

Gestor de conexiones a la base de datos. Encargado de proporcionar conexiones JDBC para realizar las operaciones de persistencia.

## Instalación y Configuración

### Requisitos Previos

- Java Development Kit (JDK) 8 o superior.
- Apache Maven.
- Servidor de aplicaciones compatible con Java (por ejemplo, Apache Tomcat).
- Sistema de gestión de bases de datos (por ejemplo, MySQL, PostgreSQL).

### Pasos para la Instalación

1. **Clonar el repositorio**:
    ```bash
    git clone https://github.com/tu_usuario/nombre_del_proyecto.git
    cd nombre_del_proyecto
    ```

2. **Configurar la base de datos**:
    - Crear una base de datos en tu sistema de gestión de bases de datos preferido.
    - Configurar las credenciales y la URL de conexión en la clase `ConnManager.java`:
        ```java
        public class ConnManager {
            private static final String URL = "jdbc:tu_sgbd://localhost:puerto/nombre_bd";
            private static final String USER = "tu_usuario";
            private static final String PASSWORD = "tu_contraseña";
        }
        ```

3. **Compilar el proyecto**:
    ```bash
    mvn clean install
    ```

4. **Desplegar el proyecto**:
    - Asegúrate de tener un servidor de aplicaciones configurado (como Apache Tomcat).
    - Despliega el archivo WAR generado en el directorio `target`.

## Uso

El sistema permite:

- **Crear una orden de compra**:
    - Proporcionar detalles del proveedor.
    - Añadir ítems a la orden.
    - Guardar y procesar la orden en la base de datos.

- **Gestionar ítems de la orden**:
    - Cada ítem incluye detalles como tipo, producto, cantidad, precio y valor total.

### Ejemplo de Uso

#### Crear y Procesar una Orden de Compra

```java
// Crear instancias de repositorios y gestor de conexiones
PurchaseOrderRepository orderRepo = new PurchaseOrderRepository();
PurchaseOrderItemRepository itemRepo = new PurchaseOrderItemRepository();
ConnManager connManager = new ConnManager();

// Crear el servicio de órdenes de compra
PurchaseOrderService orderService = new PurchaseOrderService(orderRepo, itemRepo, connManager);

// Crear un DTO para la orden de compra
PurchaseOrderDTO orderDTO = new PurchaseOrderDTO();
orderDTO.setSupplier("Proveedor XYZ");
orderDTO.setItems(Arrays.asList(
    new PurchaseOrderItemDTO("Tipo1", new RawMaterial(), 10, 5.0),
    new PurchaseOrderItemDTO("Tipo2", new RawMaterial(), 20, 2.5)
));

// Procesar la orden de compra
String result = orderService.processPurchaseOrder(orderDTO);
System.out.println(result); // "Orden de compra creada con ID: X"
```

## Contribuir

1. **Fork el repositorio**.
2. **Crea una nueva rama**:
    ```bash
    git checkout -b feature-nueva-funcionalidad
    ```
3. **Realiza tus cambios** y realiza commits descriptivos.
4. **Envía un pull request**.

## Licencia

Este proyecto está licenciado bajo la [MIT License](LICENSE).

---

**Nota**: Asegúrate de ajustar las instrucciones de instalación y configuración según las necesidades específicas de tu entorno de desarrollo y despliegue.
