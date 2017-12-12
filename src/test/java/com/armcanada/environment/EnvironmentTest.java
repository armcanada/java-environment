package com.armcanada.environment;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class EnvironmentTest
{
    private static final String ENV_KEY = "ENV_KEY";
    private static final String ENV_VALUE = "Environment value";
    @Before
    public void setUp()
    {
        Environment.clear();
        Environment.set(ENV_KEY, ENV_VALUE);
        Environment.set("SECRET_KEY", "Secret key");
    }

    @Test
    public void testHas()
    {
        assertTrue(Environment.has(ENV_KEY));
    }

    @Test
    public void testHasNot()
    {
        assertFalse(Environment.has("UNDEFINED"));
    }

    @Test
    public void testSet()
    {
        String value = "Value";
        Environment.set(ENV_KEY, value);
        assertEquals(value, Environment.get(ENV_KEY));
    }

    @Test
    public void testGet()
    {
        assertEquals(ENV_VALUE, Environment.get(ENV_KEY));
    }

    @Test
    public void testGetFallback()
    {
        String expected = "Not set";
        assertEquals(expected, Environment.get("UNDEFINED", expected));
    }

    @Test
    public void testLoad()
    {
        Environment.clear();
        List<String> envFile = new ArrayList<>();
        envFile.add("SECRET=Some secret key");
        envFile.add(String.format("%s=%s", ENV_KEY, ENV_VALUE));
        envFile.add("APP_KEY=My app key");
        Environment.load(envFile);
        assertTrue(Environment.has(ENV_KEY));
        assertEquals(ENV_VALUE, Environment.get(ENV_KEY));
    }

    @Test
    public void testAttach()
    {
        class Attachable {
            @EnvironmentVariable(key = "ENV_KEY")
            public String environment;
        }

        Attachable item = new Attachable();
        Environment.attach(item);
        assertEquals(ENV_VALUE, item.environment);
    }

    @Test
    public void testAttachWithFallback()
    {
        class Attachable {
            @EnvironmentVariable(key = "UNDEFINED", fallback = "null")
            public String environment;
        }

        Attachable item = new Attachable();
        Environment.attach(item);
        assertEquals("null", item.environment);
    }

    @Test
    public void testSize()
    {
        assertEquals(2, Environment.size());
    }

    @Test
    public void testClear()
    {
        Environment.clear();
        assertEquals(0, Environment.size());
    }
}