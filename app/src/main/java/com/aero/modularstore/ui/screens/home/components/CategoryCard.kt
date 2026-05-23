package com.aero.modularstore.ui.screens.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.aero.modularstore.model.ProductCategory

@Composable
fun CategoryFilterButtons(
	currentFilter: ProductCategory,
	onFilterChange: (ProductCategory) -> Unit,
	modifier: Modifier = Modifier
) {
	Row(
		modifier = modifier
			.fillMaxWidth()
			.height(48.dp)
			.clip(RoundedCornerShape(percent = 50))
			.background(MaterialTheme.colorScheme.surfaceDim),
		horizontalArrangement = Arrangement.spacedBy(8.dp)
	) {
		ProductCategory.entries.forEach { category ->
			CategoryFilterButton(
				label = category.label,
				isSelected = currentFilter == category,
				onClick = { onFilterChange(category) },
				modifier = Modifier.weight(1f)
			)
		}
	}
}

@Composable
private fun CategoryFilterButton(
	label: String,
	isSelected: Boolean,
	onClick: () -> Unit,
	modifier: Modifier = Modifier
) {
	Button(
		onClick = onClick,
		modifier = modifier.fillMaxHeight(),
		colors = ButtonDefaults.buttonColors(
			containerColor = if (isSelected) {
				MaterialTheme.colorScheme.primary
			} else {
				MaterialTheme.colorScheme.surfaceDim
			},
			contentColor = if (isSelected) {
				MaterialTheme.colorScheme.onPrimary
			} else {
				MaterialTheme.colorScheme.onSurface
			}
		)
	) {
		Text(label)
	}
}

@Preview(showBackground = true)
@Composable
private fun CategoryFilterButtonsPreview() {
	CategoryFilterButtons(
		currentFilter = ProductCategory.ALL,
		onFilterChange = {}
	)
}
