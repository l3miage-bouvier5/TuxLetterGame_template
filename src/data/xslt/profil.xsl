<?xml version="1.0" encoding="UTF-8"?>
<!-- <?xml-stylesheet type="text/css" href="../CSS/style-profil.css"?>-->
<!--
    Document   : profil.xsl
    Created on : 25 octobre 2022, 17:46
    Author     : bouvier et delagrange
    Description:
        Purpose of transformation follows.
-->

<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0"
                xmlns:tux = "http://myGame/tux">
        <!--<link rel="stylesheet" type="text/css" href="../CSS/style-profil.css" />-->
        
    <xsl:output method="html"/>

    <!-- TODO customize transformation rules 
         syntax recommendation http://www.w3.org/TR/xslt 
    -->
    <xsl:template match="/tux:profil"> <!-- Template pour le profil du joueur-->
        <html>
            <head>
                <title>Profil de <xsl:value-of select="tux:nom"/></title>
            </head>
            <body>
                
                <div id="card">
                    
                    <h1>
                        Profil de <xsl:value-of select="tux:nom"/>
                    </h1>
                    <div class="avatar">
                        
                        <xsl:element name="img">
                            <xsl:attribute name="src">
                                <xsl:value-of select="tux:avatar"/>
                            </xsl:attribute>
                            <xsl:attribute name="style">
                                width : 170px;
                                height : 200px;
                            </xsl:attribute>
                        </xsl:element>
                    </div>
                    <br/>
                    <div id="stats">
                        <div class="col">
                            <p class="stat"><xsl:value-of select="format-number(count(//tux:mot) div 50 *100,0)"/> %</p>
                            <p class="label">Score</p>
                        </div>
                    </div>
                    
                    <div id="bio">
                        <p>
                            &#x1F382; <xsl:value-of select="tux:anniversaire"/>
                            
                            <xsl:apply-templates select="//tux:parties/tux:partie"/>
                        </p>
                    </div>
                    <div id="buttons">
                        <button>Partager</button>
                    </div>
                </div>                
            </body>
            <style> <!-- Style css à appliquer à la page html resultante -->
                html {background-color: #6E7889;
                     font-family: tahoma, sans-serif;
                     color: #E6EBEE;}
                
                body {background-color: #6E7889;
                      font-family: tahoma, sans-serif;
                      color: #E6EBEE;}
                
                #card {background-color: #393B45;
                       height: auto;
                       width: 500px;
                       margin: 10vh auto;
                       border-radius: 25px;
                       padding-bottom: 1px;
                       box-shadow: 2px 2px 5px #4069E2;}
                
                h1 {color: white;
                    text-align: center;
                    width: 100%;
                    background-color: #E6EBEE;
                    border-radius: 25px 25px 0 0;
                    color: #393B45;
                    padding: 30px 0;
                    font-weight: 800;
                    margin: 0;}
                
                .avatar {display: block;
                         position: relative;
                         background-color: #E6EBEE;
                         width: 150px;
                         height: 150px;
                         margin: 0 auto;
                         margin-top: 30px;
                         overflow: hidden;
                         border-radius: 50%;
                         box-shadow: 1px 1px 5px #4069E2;}
                
                #bio {display: block;
                      margin: 30px auto;
                      width: 280px;
                      height: auto;}
                
                #bio p {color: #E6EBEE;
                        font-weight: lighter;
                        font-size: 15px;
                        text-align: justify;}
                
                .col {display: flex;
                      flex-direction: column;
                      justify-content: center;
                      align-items: center;
                      width: auto;}
                
                .label {margin: 0;}
                
                #buttons {
                        display: flex;
                        margin: 0 auto;
                        justify-content: space-between;
                        width: 280px;
                }

                button {
                        margin-left:10px;
                        display: block;
                        position: relative;
                        padding: 10px 0;
                        width: 260px;
                        margin: 30px auto;
                        border-radius: 25px;
                        border: none;
                        font-size: 20px;
                        letter-spacing: 0.2px;
                        font-weight: 500;
                        background-color: #4069E2;
                        color: #E6EBEE;
                }

                button:focus {
                        display: none;
                }

                button:hover {
                    transform: scale(1.03);
                    cursor: pointer;
                    transition: all 0.2s ease-in-out;
                }
                
                td {text-align:center; border-bottom:1px solid black;}
                table {
                    background-color: transparent;
                    border: 3px solid black;
                    border-radius: 7px;
                    width: 50%;
                    text-align:center;
                    margin: 30px auto;
                }
                
                #stats {
                        flex-direction: row;
                        height: auto;
                        width: 280px;
                        justify-content: space-between;
                        align-items: center;
                        margin: 0 auto;
                        font-weight: 500;
                        align: center;
                }

                .col {
                        display: flex;
                        flex-direction: column;
                        justify-content: center;
                        align-items: center;
                        width: auto;
                }

                .stat {
                        font-size: 35px;
                        margin: 0;
                }

                .label {
                        font-size: 25px;
                        margin: 0;
                }
                
            </style>
        </html>
    </xsl:template>
    
    <xsl:template match="tux:partie"> <!-- Template pour chaque partie du joueur-->
        <table>
            <caption style="width:250px"><h3> Partie du <xsl:value-of select="@date"/></h3></caption>
            <tr>
                <td>Temps :</td>
                <td><xsl:value-of select="tux:temps"/> secondes</td>
            </tr>
            <tr>
                <td>Niveau :</td>
                <td><xsl:value-of select="tux:niveau/@numero"/></td>
            </tr>
            <tr>
                <td>Mot :</td>
                <td><xsl:value-of select="tux:niveau/tux:mot"/>
                </td>
            </tr>
           
        </table>
    </xsl:template>

</xsl:stylesheet>
