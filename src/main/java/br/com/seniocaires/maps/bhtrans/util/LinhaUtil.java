package br.com.seniocaires.maps.bhtrans.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ws.rs.core.Response.Status;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlDivision;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlTable;
import com.gargoylesoftware.htmlunit.html.HtmlTableCell;
import com.gargoylesoftware.htmlunit.html.HtmlTableRow;

import br.com.seniocaires.maps.bhtrans.exception.AppException;

public class LinhaUtil {

  private LinhaUtil() {
  }

  public static List<Intinerario> getIntinerarios(String linkLinha) throws AppException {
    List<Intinerario> intinerarios = new ArrayList<>();
    WebClient webClient = new WebClient(BrowserVersion.CHROME);
    webClient.getOptions().setThrowExceptionOnScriptError(false);
    webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
    webClient.getOptions().setPrintContentOnFailingStatusCode(false);
    HtmlPage pagina = null;
    
    try {
      pagina = webClient.getPage(linkLinha);
      for (Object divCollapsible : pagina.getByXPath("//div[@class='ui-collapsible-set']")) {
        String nome = ((HtmlDivision) divCollapsible).getElementsByTagName("h3").get(0).asText();
        HtmlTable tabelaIntinerario = (HtmlTable)((HtmlDivision) divCollapsible).getElementsByTagName("table").get(0);
        String[] pontos = new String[tabelaIntinerario.getElementsByTagName("tr").getLength()];
        System.out.println(tabelaIntinerario.getElementsByTagName("tr").getLength() -1);
        int indice = 0;
        for (HtmlTableRow linhaTabelaIntinerario : tabelaIntinerario.getRows()) {
          try {
            for (final HtmlTableCell cell : linhaTabelaIntinerario.getCells()) {
              System.out.println("   Found cell: " + cell.getTextContent());
            }
            pontos[indice] = "Belo Horizonte, " + ((HtmlAnchor)linhaTabelaIntinerario.getElementsByTagName("a").get(0)).getTextContent();
            System.out.println("Belo Horizonte, " + ((HtmlAnchor)linhaTabelaIntinerario.getElementsByTagName("a").get(0)).getTextContent());
            indice++;
          } catch (IndexOutOfBoundsException e) {
            continue;
          }
        }
        Intinerario intinerario = new Intinerario(nome, pontos);
        intinerarios.add(intinerario);
        System.out.println(nome);
      }
    } catch (FailingHttpStatusCodeException | IOException e) {
      Logger.getLogger(LinhaUtil.class.getName()).log(Level.SEVERE, e.getMessage(), e);
      throw new AppException(Status.BAD_GATEWAY, "Não foi encontrado o intinerário desta linha.");
    } finally {
      webClient.close();
    }
    return intinerarios;
  }

  public static Map<String, String> getLinhas() throws AppException {
    Map<String, String> linhas = new HashMap<>();
    WebClient webClient = new WebClient(BrowserVersion.CHROME);
    HtmlPage pagina = null;

    try {
      pagina = webClient.getPage("http://servicosbhtrans.pbh.gov.br/bhtrans/e-servicos/e-servicos.asp?servico=S02&opcao=ITINER%C3%81RIO%20DE%20%C3%94NIBUS");
      for (Object linkLinha : pagina.getByXPath("//a[@class='ui-link-inherit']")) {
        linhas.put(((HtmlAnchor) linkLinha).getHrefAttribute(), ((HtmlAnchor) linkLinha).asText());
      }
    } catch (FailingHttpStatusCodeException | IOException e) {
      Logger.getLogger(LinhaUtil.class.getName()).log(Level.SEVERE, e.getMessage(), e);
      throw new AppException(Status.BAD_GATEWAY, "A lista de linhas da BHTrans está indisponível.");
    } finally {
      webClient.close();
    }

    return linhas;
  }
}
