package mancala.api.controllers;

import java.util.UUID;

import jakarta.servlet.http.*;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;

import mancala.api.models.*;
import mancala.domain.IMancala;
import mancala.domain.IMancalaFactory;
import mancala.domain.Mancala;
import mancala.persistence.IMancalaRepository;

@Path("/mancala/api")
public class MancalaController {

    private IMancalaFactory factory;
    private IMancalaRepository local_repository;
    private IMancalaRepository db_repository;

    public MancalaController(IMancalaFactory factory, IMancalaRepository repository, IMancalaRepository dbRepository) {
        this.factory = factory;
        this.local_repository = repository;
        this.db_repository = dbRepository;
    }

    @Path("/start")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response start(@Context HttpServletRequest request, StartInputDTO body) {
        // Create HTTP session.
        HttpSession session = request.getSession(true);

        // Initialize game.
        IMancala mancala = factory.createNewGame(body.getPlayer1(), body.getPlayer2());
        // Create a unique ID for this game.
        String gameId = UUID.randomUUID().toString();

        // Save the ID in the HTTP session.
        session.setAttribute("gameId", gameId);

        // Save the game in the local database.
        local_repository.save(gameId, mancala);

        // Use the game to create a DTO.
        var output = new MancalaDTO(mancala);

        // Send DTO back in response.
        return Response.status(200).entity(output).build();
    }

    @Path("/play")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response play(@Context HttpServletRequest request, PlayInputDTO body) {
        // Retrieve HTTP session.
        HttpSession session = request.getSession(false);

        // Retrieve game ID.
        String gameId = (String) session.getAttribute("gameId");

        // Retrieve the game from the local database
        IMancala mancala = local_repository.get(gameId);

        // Play a pit.
        mancala.playPit(body.getIndexToPlay());

        // Use the game to create a DTO.
        MancalaDTO output = new MancalaDTO(mancala);

        // Send DTO back in response.
        return Response.status(200).entity(output).build();
    }

    @Path("/save")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response save(@Context HttpServletRequest request) {
        HttpSession session = request.getSession(false);

        String gameId = (String) session.getAttribute("gameId");
        IMancala mancala = local_repository.get(gameId);

        db_repository.save(gameId, mancala);
        return Response.status(200).entity("Game saved successfully").build();
    }

    @Path("/load")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response load(@Context HttpServletRequest request) {
        HttpSession session = request.getSession(false);

        String gameId = (String) session.getAttribute("gameId");
        IMancala mancala = db_repository.get(gameId);

        return Response.status(200).entity(new MancalaDTO(mancala)).build();
    }
}
