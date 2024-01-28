package denys.koretskyi.txt2gpx.route;

import denys.koretskyi.txt2gpx.model.TelegramApiGetFileRequest;
import denys.koretskyi.txt2gpx.model.TelegramApiGetFileResponse;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.telegram.model.IncomingMessage;
import org.apache.camel.component.telegram.model.OutgoingDocumentMessage;
import org.springframework.stereotype.Component;

import static denys.koretskyi.txt2gpx.util.FileUtil.WINDOWS_1251;
import static denys.koretskyi.txt2gpx.util.FileUtil.convertBodyBytesToCharset;
import static denys.koretskyi.txt2gpx.util.FileUtil.updateFileName;
import static org.apache.camel.Exchange.HTTP_METHOD;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;

@Component
public class Txt2gpxRouteBuilder extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        from("telegram:bots")
            .choice()
                .when(simple("${body.document} != null"))
                    .log("received message with a document: ${body}")
                    .setProperty("fileName", simple("${body.document.fileName}"))
                    .setProperty("messageId", simple("${body.messageId}"))
                    .setProperty("chatId", simple("${body.chat.id}"))
                    .setBody(exchange ->
                        new TelegramApiGetFileRequest(
                                exchange.getIn().getBody(IncomingMessage.class).getDocument().getFileId()))
                    .marshal()
                    .json()
                    .setHeader(HTTP_METHOD, constant(POST))
                    .to("{{camel.component.telegram.base-uri}}/bot{{BOT_TOKEN}}/getFile")
                    .unmarshal()
                    .json(TelegramApiGetFileResponse.class)
                    .log("getFile response: ${body}")
                    .choice()
                        .when(simple("${body.ok} != null"))
                            .setProperty("filePath", simple("${body.result.filePath}"))
                            .marshal()
                            .json()
                            .setHeader(HTTP_METHOD, constant(GET))
                            .toD("{{camel.component.telegram.base-uri}}/file/bot{{BOT_TOKEN}}/${exchangeProperty.filePath}")
                            //TODO parse .txt and convert to .gpx
                            .setBody(exchange -> {
                                OutgoingDocumentMessage outgoingMessage = new OutgoingDocumentMessage();
                                outgoingMessage.setDocument(
                                        convertBodyBytesToCharset(exchange.getIn().getBody(byte[].class), WINDOWS_1251));
                                outgoingMessage.setFilenameWithExtension(
                                        updateFileName(exchange.getProperty("fileName", String.class)));
                                return outgoingMessage;
                            })
                            .log("outgoing message: ${body}")
                        .end()
                    .endChoice()
                .otherwise()
                    .log("received message without document")
                    .setBody(constant("Please send .txt file to be converted to .gpx extension"))
                .end()
                .to("telegram:bots");
    }
}
