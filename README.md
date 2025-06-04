# HELL - sHapE modeEer tooL

HELL is a Java-based application designed to model and manipulate geometric shapes. It provides functionality to create, modify, and analyze various geometric shapes.

## Features

- Create and manipulate geometric shapes.
- Unit testing with JUnit 5.
- Mocking capabilities with Mockito.

## Key Functional Features

- **Basic Shape Rendering**  
  - Point  
  - Line  
  - Ellipse  
  - Rectangle  
  - Cube
  - Line Segment

- **Freeform Drawing**  
  - Use brush mode for freehand drawing.

- **Fill and Stroke Options**  
  - Select fill color for shapes.  
  - Choose stroke color and thickness.

- **Toolbar with Visual Feedback**  
  - Active shape type is highlighted.

- **Canvas Operations**  
  - Clear all shapes from the canvas.  
  - Undo the last action with **Ctrl+Z**.  
  - Delete the last shape with **Ctrl+X**.

- **Shape Table View**  
  - View all shapes with details in a table.  
  - Highlight and delete shapes from the table.

- **Scene File Operations**  
  - Save scene as `.txt` (editable format).  
  - Save scene as `.png` (image format).  
  - Load scene from `.txt` file.

- **Keyboard Shortcuts**
  - **Ctrl+S** – Save scene as `.txt`  
  - **Ctrl+Shift+S** – Save scene as `.png`  
  - **Ctrl+T** – Open shape table  
  - **Ctrl+L** – Load scene from file


## Requirements

- Java 21
- Maven 3.8 or higher

## How to Run

1. Clone the repository
   ```bash
   git clone https://github.com/DmytroHalai/shape-modeler-tool.git
   ```
2. Navigate to the project directory:
   ```bash
   cd shape-modeler-tool
   ```
3. Build the project using Maven:
   ```bash
    mvn clean package
    ```
4. Run the application:
    ```bash
    java -jar target/shape-modeler-tool-1.0-SNAPSHOT.jar
    ```

## How to Run Tests

1. Navigate to the project directory:
   ```bash
   cd shape-modeler-tool
   ```

2. Run the tests using Maven:
   ```bash
   mvn clean test
   ```
## License

This project is licensed under the MIT License. See the `LICENSE` file for details.
