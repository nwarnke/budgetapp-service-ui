package com.controller.advice;

import com.trn.IUserDao;
import com.trn.dto.JsonVertexLists;
import com.trn.dto.Link;
import com.trn.dto.Node;
import com.trn.dto.Vertex;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.ArrayList;

import java.util.List;
import java.util.Map;

@Controller
public class MainController {

    private IUserDao userDao;
    public static List<Vertex> vertices = new ArrayList<>();
    public final String FILE_NAME = "test_2clq_2pt.txt";

    @Inject
    public MainController(IUserDao userDao) {
        this.userDao = userDao;
    }

    @RequestMapping(value = "something", method = RequestMethod.GET)
    @ResponseBody
    public List<Map<String, Object>> getSomething(HttpServletRequest httpRequest,
                                                  HttpServletResponse httpResponse) throws IOException {
//        try {
//            httpResponse.addHeader("Access-Control-Allow-Origin",
//                    "http://localhost:9000");
//            if (!CollectionUtils.isEmpty(vertices)) {
//                return new ResponseEntity<>(convertToJsonList(), HttpStatus.OK);
//            }
//            ClassLoader classLoader = getClass().getClassLoader();
//            File file = new File(classLoader.getResource(FILE_NAME).getFile());
//            InputStream read = new FileInputStream(file);
//            BufferedReader reader = new BufferedReader(new InputStreamReader(read));
//            String line;
//            String[] nodes;
//            while ((line = reader.readLine()) != null && !line.equals("")) {
//                nodes = line.split(" ");
//                Vertex firstVertex;
//                Vertex secondVertex;
//                if (!vertices.contains(new Vertex(nodes[0]))) {
//                    vertices.add(new Vertex(nodes[0]));
//                }
//                if (!vertices.contains(new Vertex(nodes[1]))) {
//                    vertices.add(new Vertex(nodes[1]));
//                }
//                firstVertex = vertices.get(vertices.indexOf(new Vertex(nodes[0])));
//                secondVertex = vertices.get(vertices.indexOf(new Vertex(nodes[1])));
//                addEdge(firstVertex, secondVertex);
//            }
//
//            int index = 1;
//
//            final Iterator<Vertex> iterator = vertices.iterator();
//            Vertex previous = iterator.next();
//            previous.setCommunity(getCharForNumber(index));
//            Vertex next;
//            while (iterator.hasNext()) {
//                next = iterator.next();
//                if (next.getNeighbors().contains(previous)) {
//                    next.setCommunity(getCharForNumber(index));
//                } else {
//                    index++;
//                    next.setCommunity(getCharForNumber(index));
//                }
//
//                previous = next;
//            }
//
//            calculatePermanenceForAllVertices();
//            maxPermanence();
//            calculatePermanenceForAllVertices();
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        return new ResponseEntity<>(convertToJsonList(), HttpStatus.OK);
        final List<Map<String, Object>> something = userDao.getSomething();
        return something;
    }

    private JsonVertexLists convertToJsonList() {
        JsonVertexLists jsonVertexLists = new JsonVertexLists();
        for (Vertex vertex : vertices) {
            Node node = new Node();
            node.setName(vertex.getName());
            node.setGroup(vertex.getCommunity());
            node.setPermanence(vertex.getPermanence());
            jsonVertexLists.getNodes().add(node);
            for (Vertex neighbor : vertex.getNeighbors()) {
                Link link = new Link();
                link.setSource(vertices.indexOf(new Vertex(vertex.getName())));
                link.setTarget(vertices.indexOf(new Vertex(neighbor.getName())));
                link.setValue(5);
                jsonVertexLists.getLinks().add(link);
            }

        }
        return jsonVertexLists;
    }


    private static String getCharForNumber(int i) {
        return i > 0 && i < 27 ? String.valueOf((char) (i + 64)) : null;
    }

    private static void addEdge(Vertex firstVertex, Vertex secondVertex) {
        if (vertices.contains(firstVertex) && vertices.contains(secondVertex)) {
            if (!vertices.get(vertices.indexOf(firstVertex)).getNeighbors().contains(vertices.get(vertices.indexOf(secondVertex)))) {
                firstVertex.getNeighbors().add(secondVertex);
                secondVertex.getNeighbors().add(firstVertex);
            } else {
                System.out.println("Edge already exists");
            }
        } else {
            System.out.println("Vertex not found");
        }
    }

    private static void getMenu() throws IOException {
        System.out.println("Now what would you like to do now?");
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        String input = "";
        while (!input.equals("3")) {
            System.out.println("1) Add edge");
            System.out.println("2) Delete edge");
            System.out.println("3) Exit");
            input = bufferedReader.readLine();

            if (input.equals("1")) {

            } else if (input.equals("2")) {
                System.out.println("Enter two vertices to remove the edge between: ");
                input = bufferedReader.readLine();
                String[] nodes = input.split(" ");
                deleteEdge(nodes);
                maxPermanence();
                calculatePermanenceForAllVertices();
                displayPermanenceForAllVertices();
            } else if (input.equals("3")) {
                return;
            } else {
                System.out.println("Please select a value between 1-3");
            }
        }
    }

    private static void displayGraphInfo() {
        System.out.print("Vertices:\n");

        for (Vertex vertex : vertices) {
            System.out.println("Name: " + vertex.getName());
            System.out.print("Neighbors: ");
            for (Vertex neighbor : vertex.getNeighbors()) {
                System.out.print(neighbor.getName() + " ");
            }
            System.out.println();
            System.out.println("Community: " + vertex.getCommunity());
            System.out.println();
        }
    }

    private static void calculatePermanenceForAllVertices() {
        for (Vertex vertex : vertices) {
            Services.calculatePermanence(vertex);
        }
    }

    private static void displayPermanenceForAllVertices() {
        for (Vertex vertex : vertices) {
            System.out.println("Permanence for vertex " + vertex.getName() + ": " + vertex.getPermanence());
        }
    }

    private static void recalculatePermanenceWhenEdgeIsAddedOrRemovedFromLocalCommunity(String[] nodes) {
        if (vertices.contains(new Vertex(nodes[0])) && vertices.contains(new Vertex(nodes[1]))) {
            Vertex v1 = vertices.get(vertices.indexOf(new Vertex(nodes[0])));
            float oldNumberOfInternalConnections = v1.getNumOfInternalConnections();
            float oldTotalNumberOfConnections = v1.getTotalNumOfConnections();
            float oldClusteringCoefficient = v1.getClusteringCoefficient();
            Services.getMaximumNumberOfExternalConnections(v1);
            Services.getNumberOfInternalConnections(v1);
            Services.getClusteringCoefficient(v1);
            v1.setTotalNumOfConnections(v1.getNeighbors().size());
            float updatedMaximumNumberOfEdgesToExternalCommunity = v1.getMaxNumOfExternalConnections();
            float updatedNumberOfInternalConnections = v1.getNumOfInternalConnections();
            float updatedTotalNumberOfConnections = v1.getTotalNumOfConnections();
            float updatedClusteringCoefficient = v1.getClusteringCoefficient();
            float permanence = (((updatedNumberOfInternalConnections / updatedTotalNumberOfConnections) -
                    (oldNumberOfInternalConnections / oldTotalNumberOfConnections)) / updatedMaximumNumberOfEdgesToExternalCommunity)
                    + (oldClusteringCoefficient - updatedClusteringCoefficient);
            v1.setPermanence(permanence);
        } else {
            System.out.println("One or both vertices do not exist");
        }
    }

    private static void deleteEdge(String[] nodes) throws IOException {
        if (vertices.contains(new Vertex(nodes[0])) && vertices.contains(new Vertex(nodes[1]))) {
            if (vertices.get(vertices.indexOf(new Vertex(nodes[0]))).getNeighbors().contains(vertices.get(vertices.indexOf(new Vertex(nodes[1]))))) {
                vertices.get(vertices.indexOf(new Vertex(nodes[0]))).getNeighbors()
                        .remove(vertices.get(vertices.indexOf(new Vertex(nodes[1]))));
                vertices.get(vertices.indexOf(new Vertex(nodes[1]))).getNeighbors()
                        .remove(vertices.get(vertices.indexOf(new Vertex(nodes[0]))));

            } else {
                System.out.println("Edge does not exist.");
            }
        } else {
            System.out.println("One or both vertices do not exist");
        }
    }

    private static void addVertex(String input) throws IOException {
        String vertexName = input.split(" ")[0];
        String vertexCommunity = input.split(" ")[1];
        Vertex vertex = new Vertex(vertexName);
        vertex.setCommunity(vertexCommunity);
        if (vertices.contains(vertex)) {
            System.out.println("Vertex with name" + vertex.getName() + " already added.");
        } else {
            vertices.add(vertex);
        }
    }

    private static void addEdge(String input) {
        String[] nodes = input.split(" ");
        if (nodes.length > 1 && vertices.contains(new Vertex(nodes[0])) && vertices.contains(new Vertex(nodes[1]))) {
            if (!vertices.get(vertices.indexOf(new Vertex(nodes[0]))).getNeighbors().contains(vertices.get(vertices.indexOf(new Vertex(nodes[1]))))) {
                vertices.get(vertices.indexOf(new Vertex(nodes[0]))).getNeighbors()
                        .add(vertices.get(vertices.indexOf(new Vertex(nodes[1]))));
                vertices.get(vertices.indexOf(new Vertex(nodes[1]))).getNeighbors()
                        .add(vertices.get(vertices.indexOf(new Vertex(nodes[0]))));
            } else {
                System.out.println("Edge already exists");
            }
        } else {
            System.out.println("Vertex not found");
        }
    }

    private static float maxPermanence() {
        int numOfVertices = vertices.size();
        float sum = 0;
        float oldSum = -1;
        int iteration = 0;
        int maxIteration = 5;
        while (sum != oldSum && iteration < maxIteration) {
            iteration++;
            oldSum = sum;
            sum = 0;
            for (Vertex vertex : vertices) {
                Services.calculatePermanence(vertex);
                float currentPermanence = vertex.getPermanence();
                if (currentPermanence == 1) {
                    sum = sum + currentPermanence;
                } else {
                    float currentNeighborPermanence = 0;
                    for (Vertex neighbor : vertex.getNeighbors()) {
                        Services.calculatePermanence(neighbor);
                        currentNeighborPermanence = currentNeighborPermanence + neighbor.getPermanence();
                    }
                    final List<String> communities = getNeighboringCommunities(vertex);
                    String bestCommunity = vertex.getCommunity();
                    for (String community : communities) {      //For each neighboring community of V,
                        vertex.setCommunity(community);         //move the vertex into the community
                        Services.calculatePermanence(vertex);   //Calculate the permanence when the vertex is in this new community
                        float updatedPermanence = vertex.getPermanence();
                        float updatedNeighborPermanence = 0;
                        for (Vertex neighbor : vertex.getNeighbors()) {
                            Services.calculatePermanence(neighbor);     //Calculate new permanence of neighbor
                            updatedNeighborPermanence = updatedNeighborPermanence + neighbor.getPermanence();
                        }
                        if (currentPermanence < updatedPermanence && currentNeighborPermanence < updatedNeighborPermanence) {
                            bestCommunity = vertex.getCommunity();
                            currentPermanence = updatedPermanence;
                        } else {
                            vertex.setCommunity(bestCommunity);
                        }
                    }
                    sum = sum + currentPermanence;
                }
            }
        }
        return (sum / numOfVertices); //Return the permanence of the graph
    }

    private static List<String> getNeighboringCommunities(Vertex vertex) {
        List<String> communities = new ArrayList<>();
        for (Vertex vertexCom : vertices) {
            if (!vertex.getCommunity().equals(vertexCom.getCommunity()) && !communities.contains(vertexCom.getCommunity())) {
                communities.add(vertexCom.getCommunity());
            }
        }
        return communities;
    }

}
