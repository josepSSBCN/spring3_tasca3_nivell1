package org.example.repository;

import org.example.domain.*;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class Repository implements Repo{

    //region ATRIBUTES
    private File flowershopTrees = new File("DataBase\\flowershopTrees.txt");
    private File flowershopFlowers = new File("DataBase\\flowershopFlowers.txt");
    private File flowershopDecorations = new File("DataBase\\flowershopDecorations.txt");
    private File flowershopTickets = new File("DataBase\\flowershopTickets.txt");
    private File flowershopSells = new File("DataBase\\flowershopSells.txt");
    //endregion ATRIBUTES

    //region INIT
    //Metodes d'inici per esbrinar si ja existeix una base de dades i en cas negatiu imprimir el nom de la floristeria com a titol
    @Override
    public String init() {
        Path path = Path.of("DataBase");
        String filename = null;
        if (Files.isDirectory(path)) {
            try (Stream<Path> entries = Files.list(path)) {
                BufferedReader reader = new BufferedReader(new FileReader(flowershopTrees));
                String line =  reader.readLine();
                String[] parts = line.split(":");
                String[] parts2 = parts[1].split("/");
                filename = parts2[0];
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return filename;
    }

    @Override
    public void createFlowerShop(String name) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(flowershopTrees));
            writer.write("FLORISTERIA: " + name + " / ARBOLES"+System.lineSeparator());
            writer.close();
            BufferedWriter writer2 = new BufferedWriter(new FileWriter(flowershopFlowers));
            writer2.write("FLORISTERIA: " + name + " / FLORES"+System.lineSeparator());
            writer2.close();
            BufferedWriter writer3 = new BufferedWriter(new FileWriter(flowershopDecorations));
            writer3.write("FLORISTERIA: " + name + " / DECORACION"+System.lineSeparator());
            writer3.close();
            BufferedWriter writer4 = new BufferedWriter(new FileWriter(flowershopTickets));
            writer4.write("FLORISTERIA: " + name + " / TICKETS"+System.lineSeparator());
            writer4.close();
            BufferedWriter writer5 = new BufferedWriter(new FileWriter(flowershopSells));
            writer5.write("FLORISTERIA: " + name + " / VENTAS"+System.lineSeparator());
            writer5.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
    //endregion INIT

    //region BUSQUEDAS
    //metodes per comprobar si un producte o ticket existeix (en el cas de name només per productes)
    @Override
    public boolean findbyId(int id, String type) throws IOException {
        File file = switch (type) {
            case "Tree" -> flowershopTrees;
            case "Flower" -> flowershopFlowers;
            case "Decoration" -> flowershopDecorations;
            case "Ticket" -> flowershopTickets;
            case "Sell" -> flowershopSells;
            default -> null;
        };

        BufferedReader reader = new BufferedReader(new FileReader(file));
        String line;
        boolean found = false;

        while ((line = reader.readLine()) != null) {
            if (line.startsWith(Integer.toString(id))) {
                found = true;
            }
        }
        reader.close();
        return found;
    }
    @Override
    public boolean findbyName(String name, String type) throws IOException {
        File file = switch (type) {
            case "Tree" -> flowershopTrees;
            case "Flower" -> flowershopFlowers;
            case "Decoration" -> flowershopDecorations;
            case "Ticket" -> flowershopTickets;
            case "Sell" -> flowershopSells;
            default -> null;
        };
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String line;
        boolean found = false;

        while ((line = reader.readLine()) != null) {
            if (line.contains(name)) {
                found = true;
            }
        }
        reader.close();
        return found;
    }
    // endregion BUSQUEDAS

    //region GET
    // metodes per obtindre un o més productes o ventes
    @Override
    public List<Product> getAllProducts() throws IOException {
        BufferedReader reader;
        String line;
        ArrayList<Product> products = new ArrayList<>();

        reader = new BufferedReader(new FileReader(flowershopTrees));
        reader.readLine();
        while ((line = reader.readLine()) != null) {
                String[] parts = line.split(";");
                Tree tree = new Tree(Integer.parseInt(parts[0]), parts[1], Double.parseDouble(parts[2]),
                        Integer.parseInt(parts[3]), Double.parseDouble(parts[4]));
                products.add(tree);

        }
        reader = new BufferedReader(new FileReader(flowershopFlowers));
        reader.readLine();
        while ((line = reader.readLine()) != null) {
            String[] parts = line.split(";");
            Flower flower = new Flower(Integer.valueOf(parts[0]), parts[1], Double.valueOf(parts[2]),
                    Integer.valueOf(parts[3]), parts[4]);
            products.add(flower);
        }
        reader = new BufferedReader(new FileReader(flowershopDecorations));
        reader.readLine();
        while ((line = reader.readLine()) != null) {
            String[] parts = line.split(";");
            Decoration decoration = new Decoration(Integer.valueOf(parts[0]), parts[1], Double.valueOf(parts[2]),
                    Integer.valueOf(parts[3]), parts[4]);
            products.add(decoration);
        }
        reader.close();
        return products;
    }

    @Override
    public List<Ticket> getAllSells() throws IOException {
    BufferedReader reader = new BufferedReader(new FileReader(flowershopSells));
    String line;
    ArrayList<Ticket> sells = new ArrayList<>();
        reader.readLine();
        while ((line = reader.readLine()) != null) {
            String[] parts = line.split(";");
            Ticket sell = new Ticket(Integer.valueOf(parts[0]), Double.valueOf(parts[1]));
            sells.add(sell);
        }

        return sells;
}

    @Override
    public Product getById(int id, String type) throws IOException {
        File file = null;
        BufferedReader reader = null;
        String line = null;
        switch (type){
            case "Tree":
                reader = new BufferedReader(new FileReader(flowershopTrees));
                while ((line = reader.readLine()) != null) {
                    if (line.startsWith(Integer.toString(id))){
                        String[] parts = line.split(";");
                        Tree tree = new Tree(Integer.valueOf(parts[1]), parts[2], Double.valueOf(parts[3]),
                                Integer.valueOf(parts[4]), Double.valueOf(parts[5]));
                        return tree;
                    }
                }
                break;
            case "Flower":
                reader = new BufferedReader(new FileReader(flowershopFlowers));
                while ((line = reader.readLine()) != null) {
                    if (line.startsWith(Integer.toString(id))){
                        String[] parts = line.split(";");
                        Flower flower = new Flower(Integer.valueOf(parts[1]), parts[2], Double.valueOf(parts[3]),
                                Integer.valueOf(parts[4]), parts[5]);
                        return flower;
                    }
                }
                break;
            case "Decoration":
                reader = new BufferedReader(new FileReader(flowershopDecorations));
                while ((line = reader.readLine()) != null) {
                    if (line.startsWith(Integer.toString(id))){
                        String[] parts = line.split(";");
                        Decoration decoration = new Decoration(Integer.valueOf(parts[1]), parts[2], Double.valueOf(parts[3]),
                                Integer.valueOf(parts[4]), parts[5]);
                        return decoration;
                    }
                }
                break;
        }

        return null;
    }
    //endregion GET

    //region CREATE
    //aqueste metodes creen noves entrades en el txt pertinent
    @Override
    public void createFlower(Flower flower) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(flowershopFlowers, true));
        StringBuilder newflower = new StringBuilder();
        newflower.append(assignId(flowershopFlowers) + ";");
        newflower.append(flower.getName()  + ";");
        newflower.append(flower.getPrice()  + ";");
        newflower.append(flower.getQuantity()  + ";");
        newflower.append(flower.getColor());
        writer.write(newflower.toString() +System.lineSeparator());
        writer.close();

    }

    @Override
    public void createTree(Tree tree) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(flowershopTrees, true));
        StringBuilder newtree = new StringBuilder();
        newtree.append(assignId(flowershopTrees) + ";");
        newtree.append(tree.getName() + ";");
        newtree.append(tree.getPrice() + ";");
        newtree.append(tree.getQuantity() + ";");
        newtree.append(tree.getHigh());
        writer.write(newtree.toString()+System.lineSeparator());
        writer.close();

    }

    @Override
    public void createDeco(Decoration decoration) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(flowershopDecorations, true));
        StringBuilder newdeco = new StringBuilder();
        newdeco.append(assignId(flowershopDecorations) + ";");
        newdeco.append(decoration.getName() + ";");
        newdeco.append(decoration.getPrice() + ";");
        newdeco.append(decoration.getQuantity() + ";");
        newdeco.append(decoration.getMaterial());
        writer.write(newdeco.toString()+System.lineSeparator());
        writer.close();

    }

    @Override
    public void createTicket(Ticket ticket) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(flowershopTickets, true));
        StringBuilder newticket = new StringBuilder();
        newticket.append(assignId(flowershopTickets) + ";");
       for (ProductforSale pfs : ticket.getProductforSales()){
           newticket.append(pfs.getProduct().getId() + ";");
           newticket.append(pfs.getQuantity());
       }
        writer.write(newticket.toString()+System.lineSeparator());
        writer.close();
        createSell(ticket);
    }

    @Override
    public void createSell(Ticket sell) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(flowershopSells, true));
        StringBuilder newsell = new StringBuilder();
        newsell.append(assignId(flowershopSells) + ";");
        newsell.append(sell.getTotalPrice());
        writer.write(newsell.toString() + System.lineSeparator());
        writer.close();
    }
    //endregion CREATE

    //region UPDATE
    @Override
    public void updateTree(Tree tree) throws IOException {
        StringBuilder newtree = new StringBuilder();
        newtree.append(tree.getId() + ";");
        newtree.append(tree.getName() + ";");
        newtree.append(tree.getPrice() + ";");
        newtree.append(tree.getQuantity() + ";");
        newtree.append(tree.getHigh());

        BufferedReader reader = new BufferedReader(new FileReader(flowershopTrees));
        StringBuilder originalFileContent = new StringBuilder();
        String line = null;
        while ((line = reader.readLine())  != null){
            if(!line.isBlank()) {
                originalFileContent.append(line + System.lineSeparator());
            }
        }
        String oldline = getTreeString(tree);
        String modifiedFileContent = originalFileContent.toString().replaceAll(oldline, newtree.toString());
        BufferedWriter writer = new BufferedWriter(new FileWriter(flowershopTrees));
        writer.write(modifiedFileContent);
        writer.close();

    }

    @Override
    public void updateFlower(Flower flower) throws IOException {
        StringBuilder newflower = new StringBuilder();
        newflower.append(flower.getId() + ";");
        newflower.append(flower.getName() + ";");
        newflower.append(flower.getPrice() + ";");
        newflower.append(flower.getQuantity() + ";");
        newflower.append(flower.getColor());

        BufferedReader reader = new BufferedReader(new FileReader(flowershopFlowers));
        StringBuilder originalFileContent = new StringBuilder();
        String line = null;
        while ((line = reader.readLine())  != null){
            if(!line.isEmpty()) {
                originalFileContent.append(line + System.lineSeparator());
            }
        }
        String oldline = getFlowerString(flower);
        String modifiedFileContent = originalFileContent.toString().replaceAll(oldline, newflower.toString());
        BufferedWriter writer = new BufferedWriter(new FileWriter(flowershopFlowers));
        writer.write(modifiedFileContent);
        writer.close();

    }

    @Override
    public void updateDeco(Decoration decoration) throws IOException {
        StringBuilder newdeco = new StringBuilder();
        newdeco.append(decoration.getId() + ";");
        newdeco.append(decoration.getName() + ";");
        newdeco.append(decoration.getPrice() + ";");
        newdeco.append(decoration.getQuantity() + ";");
        newdeco.append(decoration.getMaterial());

        BufferedReader reader = new BufferedReader(new FileReader(flowershopDecorations));
        StringBuilder originalFileContent = new StringBuilder();
        String line = null;
        while ((line = reader.readLine())  != null){
            if(!line.isEmpty()) {
                originalFileContent.append(line + System.lineSeparator());
            }
        }
        String oldline = getDecoString(decoration);
        String modifiedFileContent = originalFileContent.toString().replaceAll(oldline, newdeco.toString());
        BufferedWriter writer = new BufferedWriter(new FileWriter(flowershopDecorations));
        writer.write(modifiedFileContent);
        writer.close();
    }
    //endregion UPDATE

    //region METODOS AUXILIARES
    public String getTreeString(Tree tree) throws IOException {
        boolean exit = false;
        BufferedReader reader = new BufferedReader(new FileReader(flowershopTrees));
        String line;
        String productString = null;

        while ((line = reader.readLine()) != null && !exit) {
            if(line.startsWith(tree.getId() + ";")){
               productString = line;
               exit = true;
             }
        }
            return productString;
    }
    public String getFlowerString(Flower flower) throws IOException {
        boolean exit = false;
        BufferedReader reader = new BufferedReader(new FileReader(flowershopFlowers));
        String line;
        String productString = null;

        while ((line = reader.readLine())  != null && !exit) {
            if (line.startsWith(flower.getId() + ";")){
                productString = line;
                exit = true;
            }
        }
        return productString;
    }
    public String getDecoString(Decoration decoration) throws IOException {
        boolean exit = false;
        BufferedReader reader = new BufferedReader(new FileReader(flowershopDecorations));
        String line;
        String productString = null;

        while ((line = reader.readLine())  != null && !exit) {
            if (line.startsWith(decoration.getId()+";")){
                productString = line;
                exit = true;
            }
        }
        return productString;
    }
    public int assignId(File file) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String line;
        int id = 0;
        reader.readLine();
        while ((line = reader.readLine()) != null ) {
            String[] parts = line.split(";");
            id = Integer.valueOf(parts[0]);
        }
        id++;
        return id;
    }

    //endregion METODOS AUXILIARES
}
