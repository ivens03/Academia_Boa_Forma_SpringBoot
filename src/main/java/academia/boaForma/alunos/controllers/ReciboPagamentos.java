package academia.boaForma.alunos.controllers;

import academia.boaForma.alunos.models.pagamentos.PagamentosAlunosModel;
import academia.boaForma.alunos.repositories.PagamentosAlunosRepositorie;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

@RestController
@RequestMapping("/recibo")
public class ReciboPagamentos {

    private final PagamentosAlunosRepositorie pagamentosAlunosRepositorie;

    public ReciboPagamentos(PagamentosAlunosRepositorie pagamentosAlunosRepositorie) {
        this.pagamentosAlunosRepositorie = pagamentosAlunosRepositorie;
    }

    @GetMapping("/{id}/pdf")
    public ResponseEntity<byte[]> pegarRecibo(@PathVariable("id") Integer idPagamento){
        PagamentosAlunosModel reciboPdf = pagamentosAlunosRepositorie.pagamentosAlunosById(idPagamento);

        if (reciboPdf == null) {
            return ResponseEntity.notFound().build();
        }

        byte[] recibo = geracaoPdf(reciboPdf);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData(
                "filename",
                "recibo-pagamento-" + idPagamento + ".pdf"
        );
        return new ResponseEntity<>(recibo, headers, HttpStatus.OK);
    }

    private byte[] geracaoPdf(PagamentosAlunosModel pagamentosAlunosModel) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            Document document = new Document();
            PdfWriter.getInstance(document, baos);
            document.open();

            ClassLoader classLoader = getClass().getClassLoader();
            String imagePath = "static/images/logoAcademiaBoaForma(1).png";

            try (InputStream is = classLoader.getResourceAsStream(imagePath)) {
                if (is == null) {
                    throw new FileNotFoundException("Imagem não encontrada: " + imagePath);
                }

                ByteArrayOutputStream buffer = new ByteArrayOutputStream();
                int nRead;
                byte[] data = new byte[1024];
                while ((nRead = is.read(data, 0, data.length)) != -1) {
                    buffer.write(data, 0, nRead);
                }
                buffer.flush();

                // Criar a imagem
                // O valor 100 é em Pixel não em porcentagem, fazer a adquação conforme necessário
                Image logo = Image.getInstance(buffer.toByteArray());
                logo.scaleToFit(100, 100);
                logo.setAlignment(Element.ALIGN_CENTER);
                document.add(logo);
            }

            document.add(new Paragraph("Recibo de Pagamento boa forma"));
            document.add(new Paragraph("Valor: R$ " + pagamentosAlunosModel.getValor_pago()));
            document.add(new Paragraph("Forma de pagamento: " + pagamentosAlunosModel.getTipoPagamento()));
            document.add(new Paragraph("Data de pagamento efetuado: " + pagamentosAlunosModel.getData_pagamento_efetuado()));
            document.add(new Paragraph("Pagamento feito pelo aluno: " + pagamentosAlunosModel.getAluno().getNome()));

            document.close();
            return baos.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException("Erro ao gerar PDF: " + e.getMessage(), e);
        }
    }
}
