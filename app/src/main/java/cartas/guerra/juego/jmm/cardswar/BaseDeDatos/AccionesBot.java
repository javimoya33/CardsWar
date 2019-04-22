package cartas.guerra.juego.jmm.cardswar.BaseDeDatos;

import android.provider.Settings;
import android.widget.TextView;

import java.util.UUID;

import cartas.guerra.juego.jmm.cardswar.MainActivity;

/**
 * Created by Acer on 10/09/2018.
 */
public class AccionesBot {

    private static AccionesBot instance;

    // Booleans que determinan si el vehículo del jugador está ya en el tablero o no.
    public boolean veh1JugConseguido = false; public boolean veh2JugConseguido = false; public boolean veh3JugConseguido = false; public boolean veh4JugConseguido = false; public boolean vehMulticolorJugConseguido = false;
    // Integer que determina el número de vehículos del jugador ya sacados en el tablero.
    public int numJugVehConseguidos = 0;

    // Booleans que determinan si el vehículo del bot está ya en el tablero o no.
    public boolean veh1Bot1Conseguido = false; public boolean veh1Bot2Conseguido = false; public boolean veh1Bot3Conseguido = false; public boolean veh1Bot4Conseguido = false; public boolean veh1Bot5Conseguido = false;
    public boolean veh2Bot1Conseguido = false; public boolean veh2Bot2Conseguido = false; public boolean veh2Bot3Conseguido = false; public boolean veh2Bot4Conseguido = false; public boolean veh2Bot5Conseguido = false;
    public boolean veh3Bot1Conseguido = false; public boolean veh3Bot2Conseguido = false; public boolean veh3Bot3Conseguido = false; public boolean veh3Bot4Conseguido = false; public boolean veh3Bot5Conseguido = false;
    public boolean veh4Bot1Conseguido = false; public boolean veh4Bot2Conseguido = false; public boolean veh4Bot3Conseguido = false; public boolean veh4Bot4Conseguido = false; public boolean veh4Bot5Conseguido = false;
    public boolean veh5Bot1Conseguido = false; public boolean veh5Bot2Conseguido = false; public boolean veh5Bot3Conseguido = false; public boolean veh5Bot4Conseguido = false; public boolean veh5Bot5Conseguido = false;

    // Integer que determina el número de vehículos del bot ya sacados en el tablero.
    public int numBot1VehConseguidos = 0;
    public int numBot2VehConseguidos = 0;
    public int numBot3VehConseguidos = 0;
    public int numBot4VehConseguidos = 0;
    public int numBot5VehConseguidos = 0;

    public int contadorJugBatallon = 0; public int contadorJugTanque = 0; public int contadorJugSubmarino = 0; public int contadorJugCaza = 0; public int contadorJugVehMulticolor = 0;

    public int contadorBot1Batallon = 0; public int contadorBot1Tanque = 0; public int contadorBot1Submarino = 0; public int contadorBot1Caza = 0; public int contadorBot1VehMulticolor = 0;
    public int contadorBot2Batallon = 0; public int contadorBot2Tanque = 0; public int contadorBot2Submarino = 0; public int contadorBot2Caza = 0; public int contadorBot2VehMulticolor = 0;
    public int contadorBot3Batallon = 0; public int contadorBot3Tanque = 0; public int contadorBot3Submarino = 0; public int contadorBot3Caza = 0; public int contadorBot3VehMulticolor = 0;
    public int contadorBot4Batallon = 0; public int contadorBot4Tanque = 0; public int contadorBot4Submarino = 0; public int contadorBot4Caza = 0; public int contadorBot4VehMulticolor = 0;
    public int contadorBot5Batallon = 0; public int contadorBot5Tanque = 0; public int contadorBot5Submarino = 0; public int contadorBot5Caza = 0; public int contadorBot5VehMulticolor = 0;

    public double puntosJug = 0;
    public double puntosBot1 = 0; public double puntosBot2 = 0; public double puntosBot3 = 0; public double puntosBot4 = 0; public double puntosBot5 = 0;

    public int numJugInmunizados = 0; public int numJugMedicados = 0; public int numJugLimpios = 0; public int numJugVirus = 0; public int numJugNo = 4;

    public int numBot1Inmunizados = 0; public int numBot1Medicados = 0; public int numBot1Limpios = 0; public int numBot1Virus = 0; public int numBot1No = 4;
    public int numBot2Inmunizados = 0; public int numBot2Medicados = 0; public int numBot2Limpios = 0; public int numBot2Virus = 0; public int numBot2No = 4;
    public int numBot3Inmunizados = 0; public int numBot3Medicados = 0; public int numBot3Limpios = 0; public int numBot3Virus = 0; public int numBot3No = 4;
    public int numBot4Inmunizados = 0; public int numBot4Medicados = 0; public int numBot4Limpios = 0; public int numBot4Virus = 0; public int numBot4No = 4;
    public int numBot5Inmunizados = 0; public int numBot5Medicados = 0; public int numBot5Limpios = 0; public int numBot5Virus = 0; public int numBot5No = 4;


    public int[] numBotInmunizados = new int[]{ numBot1Inmunizados, numBot2Inmunizados, numBot3Inmunizados, numBot4Inmunizados, numBot5Inmunizados};
    public int[] numBotMedicados = new int[]{ numBot1Medicados, numBot2Medicados, numBot3Medicados, numBot4Medicados, numBot5Medicados};
    public int[] numBotLimpios = new int[]{ numBot1Limpios, numBot2Limpios, numBot3Limpios, numBot4Limpios, numBot5Limpios};
    public int[] numBotVirus = new int[]{ numBot1Virus, numBot2Virus, numBot3Virus, numBot4Virus, numBot5Virus};
    public int[] numBotNo = new int[]{ numBot1No, numBot2No, numBot3No, numBot4No, numBot5No};

    public double[] puntosBot = new double[]{ puntosBot1, puntosBot2, puntosBot3, puntosBot4, puntosBot5};

    public boolean[] vehBotConseguido = new boolean[]{
            veh1Bot1Conseguido, veh2Bot1Conseguido, veh3Bot1Conseguido, veh4Bot1Conseguido, veh5Bot1Conseguido,
            veh1Bot2Conseguido, veh2Bot2Conseguido, veh3Bot2Conseguido, veh4Bot2Conseguido, veh5Bot2Conseguido,
            veh1Bot3Conseguido, veh2Bot3Conseguido, veh3Bot3Conseguido, veh4Bot3Conseguido, veh5Bot3Conseguido,
            veh1Bot4Conseguido, veh2Bot4Conseguido, veh3Bot4Conseguido, veh4Bot4Conseguido, veh5Bot4Conseguido,
            veh1Bot5Conseguido, veh2Bot5Conseguido, veh3Bot5Conseguido, veh4Bot5Conseguido, veh5Bot5Conseguido};

    public int[] numBotVehConseguidos = new int[]{
            numBot1VehConseguidos, numBot2VehConseguidos, numBot3VehConseguidos, numBot4VehConseguidos, numBot5VehConseguidos};

    public int[] contadorBotBatallon = new int[]{
            contadorBot1Batallon, contadorBot2Batallon, contadorBot3Batallon, contadorBot4Batallon, contadorBot5Batallon};
    public int[] contadorBotTanque = new int[]{
            contadorBot1Tanque, contadorBot2Tanque, contadorBot3Tanque, contadorBot4Tanque, contadorBot5Tanque};
    public int[] contadorBotSubmarino = new int[]{
            contadorBot1Submarino, contadorBot2Submarino, contadorBot3Submarino, contadorBot4Submarino, contadorBot5Submarino};
    public int[] contadorBotCaza = new int[]{
            contadorBot1Caza, contadorBot2Caza, contadorBot3Caza, contadorBot4Caza, contadorBot5Caza};
    public int[] contadorBotVehMulticolor = new int[]{
            contadorBot1VehMulticolor, contadorBot2VehMulticolor, contadorBot3VehMulticolor, contadorBot4VehMulticolor, contadorBot5VehMulticolor};

    private AccionesBot(){}

    public int getNumJugInmunizados() {
        return numJugInmunizados;
    }

    public void setNumJugInmunizados(int numJugInmunizados) {
        this.numJugInmunizados = numJugInmunizados;
    }

    public int getNumJugMedicados() {
        return numJugMedicados;
    }

    public void setNumJugMedicados(int numJugMedicados) {
        this.numJugMedicados = numJugMedicados;
    }

    public int getNumJugLimpios() {
        return numJugLimpios;
    }

    public void setNumJugLimpios(int numJugLimpios) {
        this.numJugLimpios = numJugLimpios;
    }

    public int getNumJugVirus() {
        return numJugVirus;
    }

    public void setNumJugVirus(int numJugVirus) {
        this.numJugVirus = numJugVirus;
    }

    public int getNumJugNo() {
        return numJugNo;
    }

    public void setNumJugNo(int numJugNo) {
        this.numJugNo = numJugNo;
    }


    public int getNumBotInmunizados(int pos) {
        return numBotInmunizados[pos];
    }

    public void setNumBotInmunizados(int numBotInmunizados, int pos) {
        this.numBotInmunizados[pos] = numBotInmunizados;
    }

    public int getNumBotMedicados(int pos) {
        return numBotMedicados[pos];
    }

    public void setNumBotMedicados(int numBotMedicados, int pos) {
        this.numBotMedicados[pos] = numBotMedicados;
    }

    public int getNumBotLimpios(int pos) {
        return numBotLimpios[pos];
    }

    public void setNumBotLimpios(int numBotLimpios, int pos) {
        this.numBotLimpios[pos] = numBotLimpios;
    }

    public int getNumBotVirus(int pos) {
        return numBotVirus[pos];
    }

    public void setNumBotVirus(int numBotVirus, int pos) {
        this.numBotVirus[pos] = numBotVirus;
    }

    public int getNumBotNo(int pos) {
        return numBotNo[pos];
    }

    public void setNumBotNo(int numBotNo, int pos) {
        this.numBotNo[pos] = numBotNo;
    }

    public double getPuntosJug()
    {
        return this.puntosJug;
    }

    public void setPuntosJug(double ptsJug)
    {
        this.puntosJug = ptsJug;
    }

    public double getPuntosBot(int pos)
    {
        return this.puntosBot[pos];
    }

    public void setPuntosBot(double ptsBot, int pos)
    {
        this.puntosBot[pos] = ptsBot;
    }


    public boolean getVeh1JugConseguido()
    {
        return this.veh1JugConseguido;
    }

    public void setVeh1JugConseguido(boolean veh1JugCon)
    {
        this.veh1JugConseguido = veh1JugCon;
    }


    public boolean getVeh2JugConseguido()
    {
        return this.veh2JugConseguido;
    }

    public void setVeh2JugConseguido(boolean veh2JugCon)
    {
        this.veh2JugConseguido = veh2JugCon;
    }


    public boolean getVeh3JugConseguido()
    {
        return this.veh3JugConseguido;
    }

    public void setVeh3JugConseguido(boolean veh3JugCon)
    {
        this.veh3JugConseguido = veh3JugCon;
    }


    public boolean getVeh4JugConseguido()
    {
        return this.veh4JugConseguido;
    }

    public void setVeh4JugConseguido(boolean veh4JugCon)
    {
        this.veh4JugConseguido = veh4JugCon;
    }


    public boolean getVehMulticolorJugConseguido()
    {
        return this.vehMulticolorJugConseguido;
    }

    public void setVehMulticolorJugConseguido(boolean vehMultiJugCon)
    {
        this.vehMulticolorJugConseguido = vehMultiJugCon;
    }


    public int getNumJugVehConseguidos()
    {
        return this.numJugVehConseguidos;
    }

    public void setNumJugVehConseguidos(int numJugVehCon)
    {
        this.numJugVehConseguidos = numJugVehCon;
    }



    public boolean getVehBotConseguido(int pos)
    {
        return this.vehBotConseguido[pos];
    }

    public void setVehBotConseguido(boolean vehBotCon, int pos)
    {
        this.vehBotConseguido[pos] = vehBotCon;
    }

    public int getNumBotVehConseguidos(int pos)
    {
        return this.numBotVehConseguidos[pos];
    }

    public void setNumBotVehConseguidos(int numBotVehCon, int pos)
    {
        this.numBotVehConseguidos[pos] = numBotVehCon;
    }


    public int getContadorJugBatallon()
    {
        return this.contadorJugBatallon;
    }

    public void setContadorJugBatallon(int contJugBat)
    {
        this.contadorJugBatallon = contJugBat;
    }


    public int getContadorJugTanque()
    {
        return this.contadorJugTanque;
    }

    public void setContadorJugTanque(int contJugTan)
    {
        this.contadorJugTanque = contJugTan;
    }


    public int getContadorJugSubmarino()
    {
        return this.contadorJugSubmarino;
    }

    public void setContadorJugSubmarino(int contJugSub)
    {
        this.contadorJugSubmarino = contJugSub;
    }


    public int getContadorJugCaza()
    {
        return this.contadorJugCaza;
    }

    public void setContadorJugCaza(int contJugCaz)
    {
        this.contadorJugCaza = contJugCaz;
    }


    public int getContadorJugVehMulticolor()
    {
        return this.contadorJugVehMulticolor;
    }

    public void setContadorJugVehMulticolor(int contJugMulti)
    {
        this.contadorJugVehMulticolor = contJugMulti;
    }



    public int getContadorBotBatallon(int pos)
    {
        return this.contadorBotBatallon[pos];
    }

    public void setContadorBotBatallon(int contBotBat, int pos)
    {
        this.contadorBotBatallon[pos] = contBotBat;
    }


    public int getContadorBotTanque(int pos)
    {
        return this.contadorBotTanque[pos];
    }

    public void setContadorBotTanque(int contBotTan, int pos)
    {
        this.contadorBotTanque[pos] = contBotTan;
    }


    public int getContadorBotSubmarino(int pos)
    {
        return this.contadorBotSubmarino[pos];
    }

    public void setContadorBotSubmarino(int contBotSub, int pos)
    {
        this.contadorBotSubmarino[pos] = contBotSub;
    }


    public int getContadorBotCaza(int pos)
    {
        return this.contadorBotCaza[pos];
    }

    public void setContadorBotCaza(int contBotCaz, int pos)
    {
        this.contadorBotCaza[pos] = contBotCaz;
    }


    public int getContadorBotVehMulticolor(int pos)
    {
        return this.contadorBotVehMulticolor[pos];
    }

    public void setContadorBotVehMulticolor(int contBotMulti, int pos)
    {
        this.contadorBotVehMulticolor[pos] = contBotMulti;
    }

    public static synchronized AccionesBot getInstance()
    {
        if (instance == null)
        {
            instance = new AccionesBot();
        }

        return instance;
    }
}
