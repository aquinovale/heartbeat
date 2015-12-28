package br.valeconsultoriati.heartbeat.configurations;

public enum HeartbeatModel
{
    COMPANY("company"), HOST("host"), SERVICE("service"),
    CRITICO("critico"), MESSAGE("message"),
    ATUALIZADO("atualizado"), DISPARAR("disparar");

    private String value;
    HeartbeatModel(String value){
        this.value = value;
    }

    @Override
    public String toString() {
        return this.value;
    }
}


