package org.acme.quarkus.sample.writer;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import javax.json.bind.JsonbBuilder;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyWriter;
import javax.ws.rs.ext.Provider;

import org.acme.quarkus.sample.model.Person;
import org.apache.fontbox.ttf.TrueTypeCollection;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType0Font;

@Provider
@Produces("application/pdf")
public class PDFWriter implements MessageBodyWriter<Person> {

    @Override
    public boolean isWriteable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
        return true;
    }

    @Override
    public void writeTo(Person t, Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType,
            MultivaluedMap<String, Object> httpHeaders, OutputStream entityStream)
            throws IOException, WebApplicationException {

            try(TrueTypeCollection ttc = new TrueTypeCollection(PDFWriter.class.getClassLoader().getResourceAsStream("IPAGTTC00303/ipag.ttc"))){
                System.out.println("open ttc.");
                // ttc.processAllFonts(ttf -> System.out.println(ttf.getName()));

                try (PDDocument document = new PDDocument()) {
                    PDPage page = new PDPage(PDRectangle.A4);
                    document.addPage(page);
                    // PDFont font = PDType1Font.HELVETICA;
                    PDFont font = PDType0Font.load(document, ttc.getFontByName("IPAGothic"), true);
                    PDFont font_p = PDType0Font.load(document, ttc.getFontByName("IPAPGothic"), true);
                    try (PDPageContentStream cs = new PDPageContentStream(document, page)) {
                        cs.beginText();
                        cs.setFont(font_p, 12);
                        cs.newLineAtOffset(30, 600);
                        cs.showText("JSON:" + JsonbBuilder.create().toJson(t));
                        cs.newLineAtOffset(0, -100);
                        cs.showText("僕はなんだか日本語を出力してみたい気持ちに駆られたのであった。ダメね。あなたはいつもこんな調子で。");
                        cs.setFont(font, 12);
                        cs.newLineAtOffset(0, -100);
                        cs.showText("僕はなんだか日本語を出力してみたい気持ちに駆られたのであった。ダメね。あなたはいつもこんな調子で。");
                        cs.endText();
                    }
                    document.save(entityStream);
                }
               
            }
        }

}