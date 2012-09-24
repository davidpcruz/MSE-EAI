<?xml version="1.0" encoding="ISO-8859-1"?>

<!-- Edited by XMLSpy® -->

<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

    <xsl:template match="stocks">
        <html>
            <body>
              <h2>Stock Market</h2>
              <table border="1">

                    <tr bgcolor="#9acd32">

                    <th>Company</th>

                    <th>Rate</th>

                    <th>% Change</th>

                    <th>Maximum</th>

                    <th>Minimum</th>

                    <th>Quantity</th>

                    <th>Prev Close</th>

                    </tr>
              <xsl:apply-templates select="stock"/>
              </table>
            </body>
        </html>
    </xsl:template>

    <xsl:template match="stock">
          <tr>
            <td><xsl:value-of select="company/name"/></td>

            <td><xsl:value-of select="cotation/rate"/></td>

            <td><xsl:value-of select="cotation/change"/></td>
            <td><xsl:value-of select="cotation/maximum"/></td>
            <td><xsl:value-of select="cotation/minimum"/></td>

            <td><xsl:value-of select="cotation/quantity"/></td>
            <td><xsl:value-of select="cotation/prevclose"/></td>
          </tr>

    </xsl:template>
</xsl:stylesheet>

