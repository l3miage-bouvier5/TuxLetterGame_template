<?xml version="1.0" encoding="UTF-8"?>

<!--
    Document   : hiscores.xsl
    Created on : 5 décembre 2022, 14:43
    Author     : Bouvier Léo Delagrange Alex
    Description:
        Cette transformation XSLT a pour but de créer une page
        HTML à partir du fichier hiscores.xml.
        On souhaite ici faire un classement des meilleurs joueurs
        de TUX.
-->

<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" 
                xmlns:tux='http://myGame/tux' version="1.0">
    <xsl:output method="html"/>

    <xsl:template match="/"> <!-- Template pour les meilleures parties -->
        <html>
            <link rel="stylesheet" href="../css/style-hiscores.css"/>
            <head>
                <title>Classements TUX</title>
            </head>
            <body>
                <div id="card">
                    <h1>Classement des parties TUX</h1>
                    <table>
                        <tr id="titre">
                            <td>Avatar</td>
                            <td id="titrePseudo">Pseudo</td>
                            <td>Caractéristiques</td>
                        </tr>
                        
                        <xsl:apply-templates select="//tux:partie">
                            <xsl:sort select="@score" order="DESCENDING"/> <!-- Permet de trier les parties du meilleur score au moins bon -->
                        </xsl:apply-templates>
                    </table>
                </div>
            </body>
        </html>
    </xsl:template>
    <!-- Template à appliquer à toutes les parties du tableau des meilleurs scores -->
    <xsl:template match="tux:partie"> 
        <div id="presentation">

            <tr>
                <td id="colAvatar">
                    <xsl:element name="img">
                        <xsl:attribute name="src"><xsl:value-of select="tux:avatar"/></xsl:attribute>
                    </xsl:element>
                </td>
                 
                <td id="pseudo"><xsl:value-of select="tux:nom/text()"/></td>
                   
                <td id="colPseudo">
                       
                    <div id="id">
                        
                        <p id="score"><xsl:value-of select="@score"/> points</p>
                        <p>Mot : <xsl:value-of select="tux:mot/text()"/><br/>
                        Date : <xsl:value-of select="@date"/></p>
                            
                    </div>
                </td>
            </tr>
        </div>
    </xsl:template>
    
</xsl:stylesheet>
