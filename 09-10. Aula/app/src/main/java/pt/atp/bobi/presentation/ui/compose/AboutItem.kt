package pt.atp.bobi.presentation.ui.compose

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumnFor
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.AmbientContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.ui.tooling.preview.Preview
import pt.atp.bobi.R
import pt.atp.bobi.domain.AboutOptions
import pt.atp.bobi.domain.model.About

@Composable
fun AboutAppItem(about: About, onUserClick: (About) -> Unit) {
    Row(verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.clickable(onClick = { onUserClick(about) }).padding(top = 18.dp, start = 16.dp, end = 16.dp)) {

        Box(contentAlignment = Alignment.Center) {
            val image = vectorResource(id = about.image)
            Box(
                modifier = Modifier
                    .preferredSize(32.dp)
                    .clip(CircleShape)
                    .background(colorResource(id = R.color.colorPrimaryMain))
            )

            Image(
                imageVector = image,
                contentScale = ContentScale.Fit,
                modifier = Modifier.width(21.dp).height(32.dp)
            )
        }

        Column(modifier = Modifier.fillMaxWidth().padding(start = 16.dp)) {

            Text(
                text = stringResource(id = about.title),
                style = TextStyle(
                    fontSize = 19.sp,
                    fontWeight = FontWeight.Normal,
                    color = colorResource(id = R.color.black)
                ),
            )

            Text(
                stringResource(id = about.subtitle),
                style = TextStyle(
                    fontSize = 19.sp,
                    fontWeight = FontWeight.Normal,
                    color = colorResource(id = R.color.grey)
                )
            )
        }
    }
}

@Composable
fun AboutItem(about: About, onUserClick: (About) -> Unit) {
    Column {

        Row(verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.clickable(onClick = { onUserClick(about) }).padding(top = 12.dp, bottom = 12.dp, start = 16.dp, end = 16.dp)) {

            val image = vectorResource(id = about.image)

            Icon(
                imageVector = image,
                tint = colorResource(R.color.colorPrimaryMain),
                modifier = Modifier.width(28.dp).height(28.dp)
            )

            Text(
                stringResource(id = about.title),
                style = TextStyle(
                    fontSize = 19.sp,
                    fontWeight = FontWeight.Normal,
                    color = colorResource(id = R.color.black)
                ),
                modifier = Modifier.fillMaxWidth().padding(start = 16.dp)
            )
        }

    }
}

@Composable
fun AboutHeader(about: About) {
    Text(modifier = Modifier.padding(top = 24.dp, start = 16.dp, end = 16.dp),
        text = stringResource(id = about.title),
        style = TextStyle(
            fontSize = 21.sp,
            fontWeight = FontWeight.Bold,
            color = colorResource(id = R.color.colorPrimaryMain)
        )
    )
}

@Composable
fun AboutList(abouts: List<About>, onUserClick: (About) -> Unit) {
    LazyColumnFor(items = abouts) {
        when (it.type) {
            About.AboutType.APP -> {
                AboutAppItem(about = it, onUserClick)
            }
            About.AboutType.HEADER -> {
                AboutHeader(about = it)
            }
            else -> {
                AboutItem(about = it, onUserClick)
            }
        }
    }
}

fun showMessage(context: Context, about: About) {
    Toast.makeText(context, "Clicked on ${about.title}", Toast.LENGTH_SHORT).show()
}

@Composable
@Preview
fun DefaultAbout() {
    Column {
        AboutAppItem(AboutOptions.aboutOptions[0], onUserClick = {})
        AboutItem(AboutOptions.aboutOptions[1], onUserClick = {})

        AboutHeader(AboutOptions.aboutOptions[5])


        val context = AmbientContext.current
        AboutItem(AboutOptions.aboutOptions[6], onUserClick = {
            showMessage(context, AboutOptions.aboutOptions[1])
        })
    }
}