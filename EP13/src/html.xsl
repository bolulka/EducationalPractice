<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
    <xsl:variable name="apos">'</xsl:variable>

    <xsl:output method="html"/>

    <xsl:template match="/">
        <html>
            <head>
                <style>
                    table {
                    background: #FFC0CB;
                    }
                    th {
                    font-size: 14px;
                    color: black;
                    padding: 10px 12px;
                    background: white;
                    }
                    td {
                    color: black;
                    border-top: 1px solid white;
                    padding: 10px 12px;
                    background: #00FA9A;
                    transition: .3s;
                    text-align:center;
                    }
                    .head1{
                    color: #00FF7F;
                    background: #00FF7F;
                    }
                </style>
            </head>
            <body>
                <table>
                    <tr class="head1">
                        <td align="center"><strong>Brend</strong></td>
                        <td align="center"><strong>Model</strong></td>
                        <td align="center"><strong>Power</strong></td>
                        <td align="center"><strong>Year</strong></td>
                        <td align="center"><strong>Price</strong></td>
                    </tr>

                    <xsl:for-each select="sportcars/sportcar">
                        <tr>
                            <td><xsl:value-of select="@brend"/></td>
                            <td><xsl:value-of select="@model"/></td>
                            <td><xsl:value-of select="power"/></td>
                            <td><xsl:value-of select="year"/></td>
                            <td><xsl:value-of select="price"/></td>
                        </tr>
                    </xsl:for-each>
                    <tr>
                        <td><strong>Average price: </strong></td>
                        <td><xsl:value-of select="sum(sportcars/sportcar/price) div count (sportcars/sportcar)"/></td>
                    </tr>

                    <tr>
                        <td>Summary price: </td>
                        <td><xsl:value-of select="sum(sportcars/sportcar/price)"/></td>
                    </tr>
                </table>
            </body>
        </html>
    </xsl:template>
</xsl:stylesheet>