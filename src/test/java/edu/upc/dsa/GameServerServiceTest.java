package edu.upc.dsa;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;

import org.glassfish.grizzly.http.server.HttpServer;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class GameServerServiceTest {

    @After
    public void tearDown() {
        ServerGameManagerImpl.getInstance().clear();
        ServerGameManagerImpl.delete();
    }


    @Before
    public void setUp() {

    }
}
