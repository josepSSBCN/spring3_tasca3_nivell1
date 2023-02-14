package org.example.service;

import org.example.domain.*;
import org.example.exception.GetMethodException;
import org.example.exception.SumMethodException;

import java.io.IOException;
import java.util.List;

public interface Serv {

    //region METHODS: CHECK
    /**
     * Mètode x comprovar si hi ha suficient stock d'un producte.
     *
     * @param proSale La classe 'ProductforSale' per realitzar la comprovació.
     * @return Tipus boolena; False = no hi ha suficient stock; True: sí hi ha stock suficient.
     */
    boolean checkStock(ProductforSale proSale, String nameIn);

    /**
     * Comprova si en la llsita de ProductfoSafe ja existeixun id
     * @param proSafeListIn La llista de ProductforSafe on cercar el Id.
     * @param nameIn Un int amb el
     * @return False = No existeix en la llista; True = existeix en la llista.
     */
    boolean checkExistOnTicket(List<ProductforSale> proSafeListIn, String nameIn);

    //endregion METHODS: CHECK


    //region METHODS: CREATE
    /**
     * Mètode per crear la FlowerShop.
     *
     * @param name El string amb el nom de la floristeria.
     */
    void createFlowerShop(String name);

    /**
     * Metode per crear un nou tipus de producte, de qualsevol mena.
     *
     * @param product Necessita una classe del tipus de producte que s'ha de crear.
     * @return Tipus boolean. false=> No s'ha pogut crear el producte; true => s'ha creat el producte correctament
     */
    boolean createProduct(Product product);

    /**
     * Mètode per crear un nou tiket.
     *
     * @param ticket La classe ticket amb tota la informació.
     * @return Tipus boolean. False = Hi hagut algun error. True=> No hi hagut cap error.
     */
    boolean createTicket(Ticket ticket);

    //endregion METHODS: CREATE


    //region METHODS: GET
    /**
     * Mètode per aconseguir la llista de tots els productes
     *
     * @return Tipus List<Product>. Llista de tots els productes.
     * @throws GetMethodException En el cas que hi hagi algun error, saltarà aquesta execpció.
     */
    List<Product> getAllProducts() throws GetMethodException;

    //endregion METHODS: GET


    //region METHODS: OTHERS (INIT, SUM,...)
    /**
     * Mètode per inicialitzar la floristeria.
     *
     * @return Tipus string. Si retorna null vol dir que és la primera vegada, si no, retorna el nom de la floristeria.
     */
    String init();

    /**
     * Mètode per sumar el valor de tot el stock de la floristeria.
     *
     * @return Tipus double. El valor de la suma.
     * @throws SumMethodException En el cas que hi hagi algun error, saltarà aquesta execpció.
     */
    double sumStock() throws SumMethodException;

    /**
     * Mètode per sumar el valor de tots els productesque hi ha en el ticket.
     *
     * @param ticket Necessita una classe ticket amb tota la info.
     * @return Tipus double. El valor del ticket.
     * @throws SumMethodException En el cas que hi hagi algun error, saltarà aquesta execpció.
     */
    double sumTicket(Ticket ticket) throws SumMethodException;

    /**
     * Mètode per sumar el valor de tots els tickets que s'han creat
     *
     * @return Tipus double. El valor de la suma. NOTA! Si el valor retornat és
     * @throws SumMethodException En el cas que hi hagi algun error, saltarà aquesta execpció.
     */
    double sumAllTickets() throws SumMethodException;

    //endregion METHODS: OTHERS (INIT, SUM,...)

}
