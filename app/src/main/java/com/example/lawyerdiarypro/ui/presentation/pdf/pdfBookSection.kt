package com.example.lawyerdiarypro.ui.presentation.pdf

import androidx.compose.animation.core.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.compose.runtime.collectAsState
import com.example.lawyerdiarypro.ui.theme.BorderLight
import com.example.lawyerdiarypro.ui.theme.DeepBlue
import com.example.lawyerdiarypro.ui.theme.Emerald
import com.example.lawyerdiarypro.ui.theme.SurfaceLight
import com.example.lawyerdiarypro.ui.theme.TextMuted
import com.example.lawyerdiarypro.ui.theme.TextPrimary
import com.example.lawyerdiarypro.ui.theme.TextSecondary
import com.example.lawyerdiarypro.ui.theme.White
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

@Composable
fun PdfBooksScreen(
    navController: NavHostController,
    viewModel: PdfBooksViewModel = viewModel()
) {

    val books by viewModel.books.collectAsState()
    val searchQuery by viewModel.searchQuery.collectAsState()
    val selectedCategory by viewModel.selectedCategory.collectAsState()

    // Animation states
    val screenAnimation = remember { Animatable(0f) }

    LaunchedEffect(key1 = true) {
        screenAnimation.animateTo(
            targetValue = 1f,
            animationSpec = tween(800, easing = FastOutSlowInEasing)
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(SurfaceLight)
            .alpha(screenAnimation.value)
    ) {
        // Header
        Surface(
            color = White,
            shadowElevation = 4.dp,
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(horizontal = 20.dp, vertical = 16.dp)
            ) {
                Text(
                    text = "Law Library",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.ExtraBold,
                    color = DeepBlue,
                    fontSize = 28.sp
                )

                Box(
                    modifier = Modifier
                        .fillMaxWidth(0.2f)
                        .height(4.dp)
                        .padding(top = 4.dp)
                        .background(
                            Brush.horizontalGradient(
                                colors = listOf(Emerald, DeepBlue)
                            )
                        )
                )

                Text(
                    text = "Legal references & resources",
                    style = MaterialTheme.typography.bodyMedium,
                    color = TextSecondary,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }
        }

        // Search Bar
        Surface(
            color = White,
            shape = RoundedCornerShape(16.dp),
            shadowElevation = 2.dp,
            border = BorderStroke(1.dp, BorderLight),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 16.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(horizontal = 16.dp)
            ) {
                Icon(
                    Icons.Default.Search,
                    contentDescription = null,
                    tint = TextMuted,
                    modifier = Modifier.size(20.dp)
                )

                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = { viewModel.updateSearchQuery(it) },
                    modifier = Modifier
                        .weight(1f)
                        .padding(vertical = 4.dp),
                    placeholder = {
                        Text(
                            "Search by title, author or category...",
                            color = TextMuted,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color.Transparent,
                        unfocusedBorderColor = Color.Transparent,
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent
                    ),
                    singleLine = true
                )

                if (searchQuery.isNotEmpty()) {
                    IconButton(
                        onClick = { viewModel.clearSearch() },
                        modifier = Modifier.size(24.dp)
                    ) {
                        Icon(
                            Icons.Default.Close,
                            contentDescription = "Clear",
                            tint = TextMuted,
                            modifier = Modifier.size(16.dp)
                        )
                    }
                }
            }
        }

        // Category Chips
        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(viewModel.categories) { category ->
                FilterChip(
                    selected = selectedCategory == category,
                    onClick = {
                        viewModel.selectCategory(
                            if (selectedCategory == category) null else category
                        )
                    },
                    label = {
                        Text(
                            category,
                            fontSize = 13.sp,
                            fontWeight = if (selectedCategory == category)
                                FontWeight.Bold else FontWeight.Normal
                        )
                    },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = DeepBlue,
                        selectedLabelColor = White,
                        containerColor = White,
                        labelColor = TextSecondary
                    ),
                    border = BorderStroke(
                        1.dp,
                        if (selectedCategory == category) DeepBlue else BorderLight
                    )
                )
            }
        }

        // Books Grid
// Books Grid
        if (books.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 80.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        Icons.Default.MenuBook,
                        contentDescription = null,
                        tint = TextMuted,
                        modifier = Modifier.size(80.dp)
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        "No books found",
                        style = MaterialTheme.typography.bodyLarge,
                        color = TextSecondary
                    )
                    Text(
                        "Try adjusting your search",
                        style = MaterialTheme.typography.bodySmall,
                        color = TextMuted
                    )
                }
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 20.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                contentPadding = PaddingValues(bottom = 80.dp, top = 8.dp)
            ) {
                itemsIndexed(books) { index, book ->  // Use itemsIndexed to get both index and book
                    PdfBookItem(
                        book = book,
                        index = index,
                        onDownload = { viewModel.downloadBook(book) },
                        onRead = { navController.navigate("pdf_viewer/${book.id}") }
                    )
                }
            }
        }
    }
}

@Composable
fun PdfBookItem(
    book: PdfBook,
    index: Int,
    onDownload: () -> Unit,
    onRead: () -> Unit
) {
    val animatedScale = remember { Animatable(0.9f) }
    val animatedAlpha = remember { Animatable(0f) }

    LaunchedEffect(key1 = true) {
        delay(index * 100L)
        animatedAlpha.animateTo(1f, tween(500))
        animatedScale.animateTo(1f, spring(Spring.DampingRatioMediumBouncy))
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .scale(animatedScale.value)
            .alpha(animatedAlpha.value)
            .clickable { onRead() },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = White),
        elevation = CardDefaults.cardElevation(2.dp),
        border = BorderStroke(1.dp, BorderLight)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Book cover/icon
            Surface(
                color = DeepBlue.copy(alpha = 0.1f),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.size(60.dp)
            ) {
                Icon(
                    Icons.Default.MenuBook,
                    contentDescription = null,
                    tint = DeepBlue,
                    modifier = Modifier.padding(16.dp)
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            // Book details
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = book.title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.ExtraBold,
                    color = TextPrimary,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Text(
                    text = book.author,
                    style = MaterialTheme.typography.bodySmall,
                    color = TextSecondary
                )

                Spacer(modifier = Modifier.height(4.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Category chip
                    Surface(
                        color = Emerald.copy(alpha = 0.1f),
                        shape = RoundedCornerShape(4.dp)
                    ) {
                        Text(
                            text = book.category,
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                            style = MaterialTheme.typography.labelSmall,
                            color = Emerald,
                            fontSize = 10.sp
                        )
                    }

                    Spacer(modifier = Modifier.width(8.dp))

                    // Pages
                    Text(
                        text = "${book.pages} pages",
                        style = MaterialTheme.typography.labelSmall,
                        color = TextMuted
                    )
                }
            }

            // Download button
            IconButton(
                onClick = onDownload,
                modifier = Modifier
                    .size(40.dp)
                    .background(
                        color = DeepBlue.copy(alpha = 0.1f),
                        shape = CircleShape
                    )
            ) {
                Icon(
                    if (book.isDownloaded) Icons.Default.CheckCircle else Icons.Default.Download,
                    contentDescription = if (book.isDownloaded) "Downloaded" else "Download",
                    tint = if (book.isDownloaded) Emerald else DeepBlue,
                    modifier = Modifier.size(20.dp)
                )
            }
        }
    }
}

// Data class for PDF books
// Data class for PDF books - make sure all parameters match
data class PdfBook(
    val id: Int,
    val title: String,
    val author: String,
    val category: String,
    val pages: Int,
    val isDownloaded: Boolean = false,
    val fileUrl: String = "" // Add default value
)

// ViewModel for PDF Books
class PdfBooksViewModel : ViewModel() {
    private val _books = MutableStateFlow<List<PdfBook>>(emptyList())
    val books: StateFlow<List<PdfBook>> = _books.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private val _selectedCategory = MutableStateFlow<String?>(null)
    val selectedCategory: StateFlow<String?> = _selectedCategory.asStateFlow()

    val categories = listOf("All", "Constitution", "Criminal Law", "Civil Law", "Corporate Law", "Tax Law")

    init {
        loadBooks()
    }

    private fun loadBooks() {
        // Sample data - with all required parameters including fileUrl
        _books.value = listOf(
            PdfBook(1, "Indian Constitution", "Dr. B.R. Ambedkar", "Constitution", 450, false, "file1.pdf"),
            PdfBook(2, "Indian Penal Code", "Various", "Criminal Law", 680, false, "file2.pdf"),
            PdfBook(3, "Code of Civil Procedure", "Various", "Civil Law", 520, false, "file3.pdf"),
            PdfBook(4, "Companies Act 2013", "Ministry of Law", "Corporate Law", 890, false, "file4.pdf"),
            PdfBook(5, "Income Tax Act", "CBDT", "Tax Law", 750, false, "file5.pdf"),
            PdfBook(6, "Evidence Act", "Various", "Criminal Law", 380, false, "file6.pdf"),
            PdfBook(7, "Contract Law", "Various", "Civil Law", 420, false, "file7.pdf"),
            PdfBook(8, "Constitutional Law", "H.M. Seervai", "Constitution", 950, false, "file8.pdf")
        )
    }

    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
        filterBooks()
    }

    fun selectCategory(category: String?) {
        _selectedCategory.value = category
        filterBooks()
    }

    fun clearSearch() {
        _searchQuery.value = ""
        filterBooks()
    }

    private fun filterBooks() {
        // Filter logic based on search query and selected category
        val allBooks = getOriginalBooks() // You'd need to store original list

        val filtered = allBooks.filter { book ->
            val matchesSearch = searchQuery.value.isEmpty() ||
                    book.title.contains(searchQuery.value, ignoreCase = true) ||
                    book.author.contains(searchQuery.value, ignoreCase = true) ||
                    book.category.contains(searchQuery.value, ignoreCase = true)

            val matchesCategory = selectedCategory.value == null ||
                    selectedCategory.value == "All" ||
                    book.category == selectedCategory.value

            matchesSearch && matchesCategory
        }

        _books.value = filtered
    }

    private fun getOriginalBooks(): List<PdfBook> {
        // Return the original unfiltered list
        return listOf(
            PdfBook(1, "Indian Constitution", "Dr. B.R. Ambedkar", "Constitution", 450, false, "file1.pdf"),
            PdfBook(2, "Indian Penal Code", "Various", "Criminal Law", 680, false, "file2.pdf"),
            PdfBook(3, "Code of Civil Procedure", "Various", "Civil Law", 520, false, "file3.pdf"),
            PdfBook(4, "Companies Act 2013", "Ministry of Law", "Corporate Law", 890, false, "file4.pdf"),
            PdfBook(5, "Income Tax Act", "CBDT", "Tax Law", 750, false, "file5.pdf"),
            PdfBook(6, "Evidence Act", "Various", "Criminal Law", 380, false, "file6.pdf"),
            PdfBook(7, "Contract Law", "Various", "Civil Law", 420, false, "file7.pdf"),
            PdfBook(8, "Constitutional Law", "H.M. Seervai", "Constitution", 950, false, "file8.pdf")
        )
    }

    fun downloadBook(book: PdfBook) {
        // Download logic - update isDownloaded status
        val updatedBooks = _books.value.map {
            if (it.id == book.id) it.copy(isDownloaded = true) else it
        }
        _books.value = updatedBooks
    }
}
