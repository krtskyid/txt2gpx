package denys.koretskyi.txt2gpx.route;

import denys.koretskyi.txt2gpx.model.telegram.TelegramApiGetFileRequest;
import denys.koretskyi.txt2gpx.model.telegram.TelegramApiGetFileResponse;
import denys.koretskyi.txt2gpx.service.Txt2GpxFileService;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.telegram.model.IncomingMessage;
import org.apache.camel.component.telegram.model.OutgoingDocumentMessage;
import org.springframework.stereotype.Component;

import static denys.koretskyi.txt2gpx.util.Constants.CONVERTED_GPX_EXTENSION;
import static denys.koretskyi.txt2gpx.util.Constants.TXT_EXTENSION;
import static org.apache.camel.Exchange.HTTP_METHOD;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;

@Component
public class Txt2gpxRouteBuilder extends RouteBuilder {

    @Override
    public void configure() {
        from("telegram:bots")
            .choice()
                .when(simple("${body.document} != null"))
                    .log("received message with a document: ${body}")
                    .setProperty("fileName", simple("${body.document.fileName}"))
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
                            .bean(Txt2GpxFileService.class, "convertTo1251Charset")
                            .bean(Txt2GpxFileService.class, "splitByLines")
                            .bean(Txt2GpxFileService.class, "txtToGpx")
                            .marshal()
                            .jaxb()
                            .setBody(exchange -> {
                                OutgoingDocumentMessage outMessage = new OutgoingDocumentMessage();
                                outMessage.setDocument(exchange.getIn().getBody(byte[].class));
                                outMessage.setFilenameWithExtension(
                                        exchange.getProperty("fileName", String.class)
                                                .replaceAll(TXT_EXTENSION, CONVERTED_GPX_EXTENSION));
                                return outMessage;
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
