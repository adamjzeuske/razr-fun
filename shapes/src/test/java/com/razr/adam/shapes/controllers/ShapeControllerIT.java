package com.razr.adam.shapes.controllers;

import com.razr.adam.shapes.entities.Circle;
import com.razr.adam.shapes.entities.Square;
import java.util.List;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ShapeControllerIT {
    @Autowired 
    private TestRestTemplate template;

    @Test
    public void saveCircle() {
        Circle newCircle = new Circle(4);
        ResponseEntity<Circle> response = template.postForEntity(
                "/shapes/circle", 
                newCircle, 
                Circle.class);

        assertNotNull(response.getBody());
        assertNotEquals(response.getBody().getId().longValue(), 0L);
        assertNotEquals(response.getBody().getArea(), 16L);
    }

    @Test
    public void saveAndGetCircle() {
        Circle newCircle = new Circle(4);
        ResponseEntity<Circle> response = template.postForEntity(
                "/shapes/circle", 
                newCircle, 
                Circle.class);

        assertNotNull(response.getBody());
        Long id = response.getBody().getId();
        assertNotEquals(id.longValue(), 0L);
        double area = response.getBody().getArea();
        assertEquals(response.getBody().getArea(), 50.0, 1.0);


        response = template.getForEntity(
                "/shapes/circle/" + id.longValue(), 
                Circle.class);
        assertEquals(id.longValue(), response.getBody().getId().longValue());
        assertEquals(response.getBody().getArea(), area, 0);
    }

    @Test
    public void saveAndGetSquare() {
        Square newSquare = new Square(4);
        ResponseEntity<Square> response = template.postForEntity(
                "/shapes/square", 
                newSquare, 
                Square.class);

        assertNotNull(response.getBody());
        Long id = response.getBody().getId();
        assertNotEquals(id.longValue(), 0L);
        double area = response.getBody().getArea();
        assertEquals(response.getBody().getArea(), 16.0, 1.0);


        response = template.getForEntity(
                "/shapes/square/" + id.longValue(), 
                Square.class);
        assertEquals(id.longValue(), response.getBody().getId().longValue());
        assertEquals(response.getBody().getArea(), area, 0);
    }

    @Test
    public void saveAndDeleteGetSquare() {
        // CREATE
        Square newSquare = new Square(4);
        ResponseEntity<Square> response = template.postForEntity(
                "/shapes/square", 
                newSquare, 
                Square.class);

        assertNotNull(response.getBody());
        Long id = response.getBody().getId();
        assertNotEquals(id.longValue(), 0L);
        assertEquals(response.getBody().getArea(), 16.0, 1.0);
        Square theSquare = response.getBody();

        // DELETE
        HttpEntity<Square> entity = new HttpEntity(theSquare);
        template.delete("/shapes/square/" + id.longValue());
        
        // GET...should be null
        response = template.getForEntity(
                "/shapes/square/" + id.longValue(), 
                Square.class);
        assertNull(response.getBody());

    }
    
    @Test 
    public void getBundle() {
        // This is indeed a bit suboptimal, as Jackson can't easily deserialize
        // to an interface...marking SubTypes on the interface MAY work, but it
        // might not...
        ResponseEntity<List<Object>> response = template.exchange(
                "/shapes/bundle", 
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Object>>(){});
        List<Object> bundle = response.getBody();
        
        assertEquals(bundle.size(), 100);
    }
}
