package org.cross.cite.ui.theme

import androidx.compose.ui.graphics.Color
import org.cross.cite.domain.ResourceType

val ResourceType.color: Color
    get() = when (this) {
        ResourceType.Dataset -> Color(0xFF1E88E5)
        ResourceType.Software -> Color(0xFF8E24AA)
        ResourceType.Text -> Color(0xFF43A047)
        ResourceType.JournalArticle -> Color(0xFF00897B)
        ResourceType.Collection -> Color(0xFFF4511E)
        ResourceType.Image -> Color(0xFFD81B60)
        ResourceType.Other -> Color(0xFF757575)
    }
