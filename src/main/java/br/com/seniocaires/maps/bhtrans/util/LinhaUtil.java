package br.com.seniocaires.maps.bhtrans.util;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ws.rs.core.Response.Status;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

import br.com.seniocaires.maps.bhtrans.exception.AppException;

public class LinhaUtil {

  private LinhaUtil() {
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
