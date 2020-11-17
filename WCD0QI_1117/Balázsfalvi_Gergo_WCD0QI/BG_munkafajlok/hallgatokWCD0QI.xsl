<?xml version = "1.0" encoding = "UTF-8"?>
<xsl:stylesheet version = "1.0"
                xmlns:xsl = "http://www.w3.org/1999/XSL/Transform">
    <xsl:template match = "/">
        -->
        <html>
            <body>
                <h2>Employee</h2>
                <table border = "1">
                    <tr bgcolor = "green">
                        <th>ID</th>
                        <th>First Name</th>
                        <th>Last Name</th>
                        <th>Nick Name</th>
                        <th>Salary</th>
                    </tr>
                    <xsl:for-each select="osztaly/alkalmazott">
                        <tr>
                            <td>
                                <xsl:value-of select = "@id"/>
                            </td>
                            <td><xsl:value-of select = "keresznev"/></td>
                            <td><xsl:value-of select = "vezeteknev"/></td>
                            <td><xsl:value-of select = "becenev"/></td>
                            <td><xsl:value-of select = "fizetes"/></td>
                        </tr>
                    </xsl:for-each>
                </table>
            </body>
        </html>
    </xsl:template>
</xsl:stylesheet>