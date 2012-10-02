<?xml version="1.0" encoding="UTF-8"?>

<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:mse="msejdf">
	<xsl:output method="html" indent="no"/>
	<xsl:template match="mse:stocks">
		<html>
			<body>
				<h2>Stock Market</h2>
				<table border="1">
					<tr bgcolor="#9acd32">
						<th>Company</th>
						<th>Prev Close</th>
						<th>% Variation</th>
						<th>Maximum</th>
						<th>Minimum</th>
						<th>Quantity</th>
						<th>Time</th>
						<th>Purchase Rate</th>
						<th>Sell Rate</th>
					</tr>
					<xsl:apply-templates select="mse:stock" />
				</table>
			</body>
		</html>
	</xsl:template>
	<xsl:template match="mse:stock">
		<tr>
			<td>
				<xsl:value-of select="mse:company/mse:name" />
			</td>
			<td>
				<xsl:value-of select="mse:cotation/mse:lastCotation" />
			</td>
			<td>
				<xsl:value-of select="mse:cotation/mse:variation" />
			</td>
			<td>
				<xsl:value-of select="mse:cotation/mse:maximum" />
			</td>
			<td>
				<xsl:value-of select="mse:cotation/mse:minimum" />
			</td>
			<td>
				<xsl:value-of select="mse:cotation/mse:quantity" />
			</td>
			<td>
				<xsl:value-of select="mse:cotation/mse:time" />
			</td>
			<td>
				<xsl:value-of select="mse:cotation/mse:purchase" />
			</td>
			<td>
				<xsl:value-of select="mse:cotation/mse:sell" />
			</td>
		</tr>
	</xsl:template>
</xsl:stylesheet>