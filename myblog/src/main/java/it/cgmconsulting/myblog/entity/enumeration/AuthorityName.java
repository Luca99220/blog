package it.cgmconsulting.myblog.entity.enumeration;

public enum AuthorityName {

    ADMIN, //amministratore
    WRITER, //scrive post
    MEMBER, //scrive commenti e vota i post
    MODERATOR, //si occupa delle segnalazioni
    GUEST //utente non registrato
}
