
package com.sagen.balloonlander.server;

import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jettison.json.JSONObject;

import javax.jws.WebParam;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.ext.Provider;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static javax.ws.rs.core.MediaType.*;

@Path("/highscore")
public class Highscore {
    private static List<Map<String, Integer>> scores = new ArrayList<Map<String, Integer>>() {{
       add(new HashMap<String, Integer>(){{put("Alex", 231312);}});
       add(new HashMap<String, Integer>(){{put("Jens", 114223);}});
       add(new HashMap<String, Integer>(){{put("Per", 9928);}});
    }};

    @GET 
    @Produces(APPLICATION_JSON)
    public List list() {
        return scores;
    }


    @POST
    @Produces(TEXT_PLAIN)
    public String add(@FormParam("name") String name, @FormParam("score") int score){
        HashMap<String, Integer> map = new HashMap<String, Integer>();
        map.put(name == null ? "" : name, score);
        scores.add(map);
        return "ok";
    }
}
