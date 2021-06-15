package dmp.staffadmin.metier.enumeration;

public enum TypeDepartEnum
{
    RETRAITE("Retraite"),
    DEMISSION("Démission"),
    LICENCIEMENT("Licenciement"),
    REVOCATION("Révocation"),
    MISE_A_DISPOSITION("Mise à disposition"),
    NOMINATION("Nomination"),
    DECES("Décès");

    TypeDepartEnum(String typeDepart)
    {
        this.typeDepart = typeDepart;
    }
    private String typeDepart;

    public String toString() {return this.typeDepart;}
}
