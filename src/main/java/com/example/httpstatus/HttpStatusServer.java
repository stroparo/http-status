import io.vertx.core.AbstractVerticle;

public class HttpStatusServer extends AbstractVerticle {
  public void start() {
    vertx.createHttpServer().requestHandler(req -> {
      req.response()
        .putHeader("content-type", "text/plain")
        .end("Hello Vert.x yay!");
    }).listen(8080);
  }
}
