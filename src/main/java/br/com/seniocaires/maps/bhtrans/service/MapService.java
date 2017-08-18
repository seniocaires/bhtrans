package br.com.seniocaires.maps.bhtrans.service;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.google.gson.Gson;

import br.com.seniocaires.maps.bhtrans.exception.AppException;
import br.com.seniocaires.maps.bhtrans.util.LinhaUtil;

@Path("/")
public class MapService {

  @POST
  @Path("/intinerario")
  @Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
  public Response intinerario(@FormParam("link-linha") String linkLinha) {
    try {
      Gson gson = new Gson();
      return Response.ok().entity(gson.toJson(LinhaUtil.getIntinerarios(linkLinha))).build();
    } catch (AppException e) {
      return Response.status(e.getCodigo()).entity(e.getMessage()).build();
    }
  }

  @GET
  @Path("/linha")
  @Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
  public Response linha() {
    try {
      Gson gson = new Gson();
      return Response.ok().entity(gson.toJson(LinhaUtil.getLinhas())).build();
    } catch (AppException e) {
      return Response.status(e.getCodigo()).entity(e.getMessage()).build();
    }
  }
}