# 💡 Demo: Aplicación Web con Azure Custom Vision y Spring Boot

## 🎯 Objetivo

Diseñar e implementar una aplicación web que integre análisis de imágenes con **Azure Custom Vision**, usando **Java + Spring Boot**, y desplegada en **Azure App Service**.

---

## 🧩 Arquitectura del Sistema

| Componente         | Descripción                                                       |
|-------------------|-------------------------------------------------------------------|
| Microservicio API | Backend Java con Spring Boot que consume el servicio de visión    |
| Frontend Web      | Interfaz HTML + JS para subir imágenes y mostrar resultados       |
| Azure Custom Vision | Servicio IA para clasificación de imágenes entrenado previamente |
| Azure App Service | Hosting de la aplicación web                                      |

---

## 🧠 Paso 1: Preparar Azure Custom Vision

1. Accede a [https://www.customvision.ai](https://www.customvision.ai)
2. Crea un nuevo proyecto:
   - Tipo: Clasificación multiclase
   - Dominio: General o específico
3. Sube imágenes de entrenamiento y etiquétalas
4. Entrena el modelo
5. Obtén:
   - Endpoint de predicción
   - Clave de predicción
   - ID del proyecto
   - Nombre de la iteración

---

## 🛠️ Paso 2: Backend con Spring Boot

### 🗂️ Estructura del Proyecto
 ```
vision-service/
├── src/main/java/com/demo/
│   ├── VisionServiceApplication.java
│   ├── controller/VisionController.java
│   ├── service/VisionService.java
├── src/main/resources/
│   ├── application.properties
│   └── static/index.html
├── pom.xml
```

### 📄 application.properties

```properties
customvision.endpoint=https://<region>.api.cognitive.microsoft.com
customvision.key=TU_CLAVE
customvision.projectId=ID_DEL_PROYECTO
customvision.iterationName=NombreIteracion
server.port=8080
```


### ⚙️ VisionService.java
```java
@Service
public class VisionService {

    @Value("${customvision.endpoint}")
    private String endpoint;

    @Value("${customvision.projectId}")
    private String projectId;

    @Value("${customvision.iterationName}")
    private String iterationName;

    @Value("${customvision.key}")
    private String predictionKey;

    public String analyzeImage(MultipartFile file) {
        String url = String.format("%s/customvision/v3.0/Prediction/%s/classify/iterations/%s/image",
            endpoint, projectId, iterationName);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Prediction-Key", predictionKey);
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);

        try {
            HttpEntity<byte[]> entity = new HttpEntity<>(file.getBytes(), headers);
            RestTemplate restTemplate = new RestTemplate();
            return restTemplate.exchange(url, HttpMethod.POST, entity, String.class).getBody();
        } catch (IOException e) {
            return "{\"error\":\"Error procesando la imagen\"}";
        }
    }
}
```


### 🚦 VisionController.java
```java
@RestController
@RequestMapping("/api/vision")
public class VisionController {

    @Autowired
    private VisionService visionService;

    @PostMapping("/analyze")
    public ResponseEntity<?> analyze(@RequestParam("file") MultipartFile file) {
        return ResponseEntity.ok(visionService.analyzeImage(file));
    }
}
```

### 📊 PredictionResult.java
```java
public class PredictionResult {
    private String id;
    private String project;
    private String iteration;
    private String created;
    private List<Prediction> predictions;
    
    // Getters y setters...
}
```


### 📌 Prediction.java
```java
public class Prediction {
    private String tagId;
    private String tagName;
    private double probability;
    
    // Getters y setters...
}
```


## 🎨 Paso 3: Frontend Personalizado

### 📄 index.html (estático)
```html
<!DOCTYPE html>
<html lang="es">
<head>
  <meta charset="UTF-8">
  <title>Azure Custom Vision Demo</title>
  <style>
    body {
      font-family: sans-serif;
      background-color: #f4f6f8;
      padding: 40px;
    }
    h1 { color: #2a4365; }
    .container {
      background: white;
      max-width: 500px;
      margin: auto;
      padding: 30px;
      border-radius: 12px;
      box-shadow: 0 4px 12px rgba(0,0,0,0.1);
    }
    button {
      background-color: #3182ce;
      color: white;
      padding: 10px 20px;
      border: none;
      border-radius: 6px;
      cursor: pointer;
    }
    #result {
      background: #edf2f7;
      margin-top: 20px;
      padding: 15px;
      border-radius: 6px;
      font-family: monospace;
    }
  </style>
</head>
<body>
  <div class="container">
    <h1>🔍 Análisis de Imagen</h1>
    <form id="uploadForm" enctype="multipart/form-data">
      <input type="file" name="file" accept="image/*" required />
      <br /><br />
      <button type="submit">Analizar</button>
    </form>
    <div id="result"></div>
  </div>

  <script>
    document.getElementById("uploadForm").addEventListener("submit", function(e) {
      e.preventDefault();
      const formData = new FormData(e.target);
      fetch("/api/vision/analyze", {
        method: "POST",
        body: formData
      })
      .then(res => res.json())
      .then(data => {
        document.getElementById("result").innerText = JSON.stringify(data, null, 2);
      });
    });
  </script>
</body>
</html>
```

## ☁️ Paso 4: Desplegar en Azure App Service
### 🔧 Empaquetar con Maven
```mvn clean package```


### 🚀 Subir manualmente desde el Portal de Azure
- Ve al recurso App Service
- En la sección Deployment Center elige Manual Deployment
- Sube el archivo .jar generado en target/


### 🛠 Configurar variables de entorno
En “App Service → Configuration → Application Settings”, agrega:
```
customvision.key = TU_CLAVE
customvision.endpoint = TU_ENDPOINT
customvision.projectId = TU_PROJECT_ID
customvision.iterationName = TU_ITERACION
```









