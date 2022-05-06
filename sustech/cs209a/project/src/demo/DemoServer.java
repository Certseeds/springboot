package demo;

import dao.TextDao;
import io.javalin.Javalin;
import io.javalin.plugin.openapi.OpenApiOptions;
import io.javalin.plugin.openapi.OpenApiPlugin;
import io.javalin.plugin.openapi.ui.SwaggerOptions;
import io.swagger.v3.oas.models.info.Info;
import service.TextService;

/**
 * The type Demo server.
 */
public class DemoServer {
    /**
     * The Service.
     */
    static TextService service = new TextService();
    /**
     * The App.
     */
    static Javalin app = Javalin.create(config -> {
        config.registerPlugin(getConfiguredOpenApiPlugin());
    }).start(7002);

    /**
     * The entry point of application.
     * <p> this part is the router layer</>
     *
     * @param args the input arguments, it is useless.
     * @throws ClassNotFoundException the class not found exception
     */
    public static void main(String[] args) throws ClassNotFoundException {
        TextDao.initial();
        //main2();
        app.get("/", ctx -> ctx.result("Welcome to RESTful Corpus Platform"));
        // handle exist
        app.get("/files/:md5/exists", service::handleExists);
        // handle upload
        app.post("/files/:md5", service::handleUpload);
        // handle compare
        app.get("/files/:md51/compare/:md52", service::handleCompare);
        // handle download
        app.get("/files/:md5", service::handleDownload);
        // handle file list
        app.get("/files", service::handle_files);
    }

    /**
     * Gets configured open api plugin.
     *
     * @return the configured open api plugin
     */
    private static OpenApiPlugin getConfiguredOpenApiPlugin() {
        Info info = new Info().version("1.0").description("RESTful Corpus Platform API");
        OpenApiOptions options = new OpenApiOptions(info)
                .activateAnnotationScanningFor("cn.edu.sustech.java2.RESTfulCorpusPlatform")
                .path("/swagger-docs") // endpoint for OpenAPI json
                .swagger(new SwaggerOptions("/swagger-ui")); // endpoint for swagger-ui
//                .reDoc(new ReDocOptions("/redoc")); // endpoint for redoc
        return new OpenApiPlugin(options);
    }

}

