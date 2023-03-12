<?xml version="1.0" encoding="UTF-8"?>

<!--
    Document   : dico.xsl
    Created on : 25 octobre 2022, 16:00
    Author     : bouvier et delagrange
    Description:
        Purpose of transformation follows.
-->

<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0"
                xmlns:tux = "http://myGame/tux">
    <xsl:output method="html"/>

    <!-- TODO customize transformation rules 
         syntax recommendation http://www.w3.org/TR/xslt 
    -->
    <!-- On créer la template qui va donner la forme général du fichier html-->
    <xsl:template match="/">
        <html>
            <head>
                <title>Dictionnaire de jeu !</title>
            </head>
            <body>
                <h1>Dictionnaire de jeu !</h1>
                <h2>Mots du dictionnaire par ordre alphabétique</h2>
                <!-- On applique la templates qui va afficher chaque mot par ordre alphabétique du fichier -->
                <xsl:apply-templates select="//tux:niveau"/>
            </body>
        </html>
    </xsl:template>
    <!-- Template à appliquer pour chaque niveau, tri les mot par ordre alphabétique -->
    <xsl:template match="tux:niveau">
        <h3>Niveau
            <xsl:value-of select="@numero"/>
        </h3>
        <xsl:apply-templates select="tux:mot">
            <xsl:sort order = "ascendant"/>
        </xsl:apply-templates>
     
    </xsl:template>
    <!-- template de pull du dictionnaire trié
         Template à appliquer pour chaque mot -->
    <xsl:template match="tux:mot">
            <xsl:value-of select="text()"/>
            <br/>
    </xsl:template>
    
</xsl:stylesheet>
