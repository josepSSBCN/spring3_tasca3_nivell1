package org.example.service;

import org.example.domain.*;
import org.example.exception.GetMethodException;
import org.example.exception.SumMethodException;
import org.example.repository.Repository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Service implements Serv {

    //region ATTRIBUTES
    private static Repository repoCls;

    //endregion ATTRIBUTES


    //region METHODS: CHECK

    @Override
    public boolean checkStock(ProductforSale proSale, String nameIn) {
        //region DEFINITION VARIABLES
        boolean resul = false, contin = false;
        int i = 0;
        List<Decoration> decoList;
        List<Flower> flowerList;
        List<Tree> treeList;

        //endregion DEFINITION VARIABLES


        //region ACTIONS
        try {
            // 1) GET ALL PRODUCTS OF SAME TYPE
            if (proSale.getProduct().getClass() == Decoration.class) {
                decoList = new ArrayList<>(getDecoProducts());

                // 2) CHECK PRODUCT STOCK
                while (i < decoList.size() && !contin) {
                    if (decoList.get(i).getName().equals(nameIn)) {
                        contin = true;
                        resul = proSale.getQuantity() <= decoList.get(i).getQuantity();
                    }
                    i++;
                }
            } else if (proSale.getProduct().getClass() == Flower.class) {
                flowerList = new ArrayList<>(getFlowerProducts());

                // 2) CHECK PRODUCT STOCK
                while (i < flowerList.size() && !contin) {
                    if (flowerList.get(i).getName().equals(nameIn)) {
                        contin = true;
                        resul = proSale.getQuantity() <= flowerList.get(i).getQuantity();
                    }
                    i++;
                }
            } else if (proSale.getProduct().getClass() == Tree.class) {
                treeList = new ArrayList<>(getTreeProducts());

                // 2) CHECK PRODUCT STOCK
                while (i < treeList.size() && !contin) {
                    if (treeList.get(i).getName().equals(nameIn)) {
                        contin = true;
                        resul = proSale.getQuantity() <= treeList.get(i).getQuantity();
                    }
                    i++;
                }
            }

        } catch (Exception ex) {
            resul = false;
        }

        //endregion ACTIONS


        // OUT
        return resul;
    }

    @Override
    public boolean checkExistOnTicket(List<ProductforSale> proSafeListIn, String nameIn) {
        //region DEFINITION VARIABLES
        boolean resul = false, exit = false;
        int index = 0;

        //endregion DEFINITION VARIABLES


        //region ACTIONS
        if (proSafeListIn.size() > 0) {
            do {
                if (proSafeListIn.get(index).getProduct().getName().equals(nameIn)) {
                    exit = true;
                    resul = true;
                }

                index++;
            } while (proSafeListIn.size() < index && exit != true);
        }
        //endregion ACTIONS


        // OUT
        return resul;


    }

    //endregion METHODS: CHECK


    //region METHODS: CREATE

    @Override
    public void createFlowerShop(String name) {
        //region DEFINITION VARIABLES

        //endregion DEFINITION VARIABLES


        //region ACTIONS
        try {
            // INIT VARIABLES
            if (repoCls == null) {
                repoCls = new Repository();
            }

            // CALL REPOSITORY METHOD
            repoCls.createFlowerShop(name);

        } catch (Exception ex) {
            //TODO control errors
        }

        //endregion ACTIONS

    }


    @Override
    public boolean createProduct(Product product) {
        //region DEFINITION VARIABLES
        boolean resul = false;

        //endregion DEFINITION VARIABLES


        //region ACTIONS
        try {
            // INIT VARIABLES
            repoCls = new Repository();

            // CALL REPOSITORY METHODS
            if (product.getClass() == Decoration.class) {
                if (!repoCls.findbyName(product.getName(), "Decoration")) {
                    repoCls.createDeco((Decoration) product);
                    resul = true;
                }
            } else if (product.getClass() == Flower.class) {
                if (!repoCls.findbyName(product.getName(), "Flower")) {
                    repoCls.createFlower((Flower) product);
                    resul = true;
                }
            } else if (product.getClass() == Tree.class) {
                if (!repoCls.findbyName(product.getName(), "Tree")) {
                    repoCls.createTree((Tree) product);
                    resul = true;
                }
            }

        } catch (Exception ex) {
            resul = false;
        }

        //endregion ACTIONS


        // OUT
        return resul;

    }

    @Override
    public boolean createTicket(Ticket ticket) {
        //region DEFINITION VARIABLES
        boolean result;

        //endregion DEFINITION VARIABLES


        //region ACTIONS
        try {
            // INIT VARIABLES
            repoCls = new Repository();

            // 1) CALCULATE TOTAL PRICE
            ticket.setTotalPrice(sumTicket(ticket));

            // 2) SAVE TICKET
            repoCls.createTicket(ticket);

            // 3) UPDATE STOCKS
            result = updateStock(ticket);

        } catch (Exception ex) {
            result = false;
        }

        //endregion ACTIONS


        // OUT
        return result;

    }

    //endregion METHODS: CREATE


    //region METHODS: GET
    @Override
    public List<Product> getAllProducts() throws GetMethodException {
        //region DEFINITION VARIABLES
        List<Product> productList;

        //endregion DEFINITION VARIABLES


        //region ACTIONS
        try {
            // INIT VARIABLES
            repoCls = new Repository();

            // CALL REPOSITORY METHOD
            productList = new ArrayList<>(repoCls.getAllProducts());

        } catch (Exception ex) {
            throw new GetMethodException(1);
        }

        //endregion ACTIONS


        // OUT
        return productList;

    }

    //endregion METHODS: GET


    //region METHODS: OTHERS (INIT, SUM,...)

    @Override
    public String init() {
        //region DEFINITION VARIABLES
        String name = null;

        //endregion DEFINITION VARIABLES


        //region ACTIONS
        try {
            // INIT VARIABLES
            repoCls = new Repository();

            // CALL REPOSITORY METHOD
            name = repoCls.init();

        } catch (Exception ex) {
            //TODO control errors
        }

        //endregion ACTIONS


        // OUT
        return name;

    }

    @Override
    public double sumStock() throws SumMethodException {
        //region DEFINITION VARIABLES
        double result = 0;
        List<Product> productList;

        //endregion DEFINITION VARIABLES


        //region ACTIONS
        try {
            // INIT VALUES
            repoCls = new Repository();

            /// 1) GET PRODCUTS
            productList = new ArrayList<>(repoCls.getAllProducts());

            // 2) SUM SCTOCK VALUE
            for (Product p : productList) {
                result += p.getQuantity() * p.getPrice();
            }

        } catch (Exception ex) {
            throw new SumMethodException(3);
        }

        //endregion ACTIONS


        // OUT
        return result;
    }

    @Override
    public double sumTicket(Ticket ticket) throws SumMethodException {
        //region DEFINITION VARIABLES
        double result = 0.0;

        //endregion DEFINITION VARIABLES


        //region ACTIONS
        try {
            // SUM VALUES
            for (ProductforSale p : ticket.getProductforSales()) {
                result += p.getQuantity() * p.getPrice();
            }


        } catch (Exception ex) {
            throw new SumMethodException(2);
        }

        //endregion ACTIONS


        // OUT
        return result;

    }

    /**
     * Mètode per sumar el valor de tots els tiquets que s'han creat.
     *
     * @return El valor de la suma. NOTA! Si el valor retornat és
     * @throws SumMethodException En el cas que hi hagi algun error, saltarà aquesta execpció.
     */
    @Override
    public double sumAllTickets() throws SumMethodException {
        //region DEFINITION VARIABLES
        double result = 0;
        List<Ticket> ticketList;

        //endregion DEFINITION VARIABLES


        //region ACTIONS
        try {
            // INIT VARIABLES
            repoCls = new Repository();

            // 1) GET ALL TICKETS ON DDBB
            ticketList = new ArrayList<>(repoCls.getAllSells());

            // 2) SUM TICKETS VALUES
            for (Ticket t : ticketList) {
                result += t.getTotalPrice();
            }

        } catch (Exception ex) {
            throw new SumMethodException(1);
        }

        //endregion ACTIONS


        // OUT
        return result;

    }

    //endregion METHODS: OTHERS (SUM,...)


    //region PRIVATE METHODS

    /**
     * Mètode que retorna tots els prodcutes tipus Decoration.
     *
     * @return Tipus List<Decoration>. La llista amb els productes tipus Decoration.
     * @throws IOException En el cas que hi hagi algun error, saltarà aquesta execpció.
     */
    private List<Decoration> getDecoProducts() throws IOException {
        //region DEFINITION VARIABLES
        List<Decoration> decoList;
        List<Product> productList;

        //endregion DEFINITION VARIABLES


        //region ACTIONS
        // INIT VARIABLES
        decoList = new ArrayList<>();
        repoCls = new Repository();

        // 1) GET ALL PRODUCTS
        productList = new ArrayList<>(repoCls.getAllProducts());

        // 2) FIND DECORATION PRODUCTS
        for (Product p : productList) {
            if (p.getClass() == Decoration.class) {
                decoList.add((Decoration) p);
            }
        }

        //endregion ACTIONS


        // OUT
        return decoList;

    }

    /**
     * Mètode que retorna tots els prodcutes tipus Flower.
     *
     * @return Tipus List<Flower>. La llista amb els productes tipus Flower.
     * @throws IOException En el cas que hi hagi algun error, saltarà aquesta execpció.
     */
    private List<Flower> getFlowerProducts() throws IOException {
        //region DEFINITION VARIABLES
        List<Flower> flowerList;
        List<Product> productList;

        //endregion DEFINITION VARIABLES


        //region ACTIONS
        // INIT VARIABLES
        flowerList = new ArrayList<>();
        repoCls = new Repository();

        // 1) GET ALL PRODUCTS
        productList = new ArrayList<>(repoCls.getAllProducts());

        // 2) FIND DECORATION PRODUCTS
        for (Product p : productList) {
            if (p.getClass() == Flower.class) {
                flowerList.add((Flower) p);
            }
        }

        //endregion ACTIONS


        // OUT
        return flowerList;

    }

    /**
     * Mètode que retorna tots els prodcutes tipus Tree.
     *
     * @return Tipus List<Decoration>. La llista amb els productes tipus Tree.
     * @throws IOException En el cas que hi hagi algun error, saltarà aquesta execpció.
     */
    private List<Tree> getTreeProducts() throws IOException {
        //region DEFINITION VARIABLES
        List<Tree> treeList;
        List<Product> productList;

        //endregion DEFINITION VARIABLES


        //region ACTIONS
        // INIT VARIABLES
        treeList = new ArrayList<>();

        repoCls = new Repository();

        // 1) GET ALL PRODUCTS
        productList = new ArrayList<>(repoCls.getAllProducts());

        // 2) FIND DECORATION PRODUCTS
        for (Product p : productList) {
            if (p.getClass() == Tree.class) {
                treeList.add((Tree) p);
            }
        }

        //endregion ACTIONS


        // OUT
        return treeList;

    }

    /**
     * Mètode per actualitzar el stock de tots els articles
     *
     * @param ticket Classe del tipus Ticket amb la llista de tots els productes que s'han comprat.
     * @return Tipus boolean. False = hi ha hagut algun problema; True = No hi hagut cap problema.
     */
    private boolean updateStock(Ticket ticket) {
        //region DEFINITION VARIABLES
        boolean result = false;

        //endregion DEFINITION VARIABLES


        //region ACTIONS
        try {
            // INIT
            repoCls = new Repository();

            // ITERATE FOR ALL PRODUCTS
            for (ProductforSale p : ticket.getProductforSales()) {
                p.getProduct().setQuantity(p.getProduct().getQuantity() - p.getQuantity());

                if (p.getProduct().getClass() == Decoration.class) {
                    repoCls.updateDeco((Decoration) p.getProduct());
                } else if (p.getProduct().getClass() == Flower.class) {
                    repoCls.updateFlower((Flower) p.getProduct());
                } else if (p.getProduct().getClass() == Tree.class) {
                    repoCls.updateTree((Tree) p.getProduct());
                }
            }

            result = true;
        } catch (Exception ex) {
            result = false;
        }

        //endregion ACTIONS


        // OUT
        return result;

    }

    //endregion PRIVATE METHODS


}

