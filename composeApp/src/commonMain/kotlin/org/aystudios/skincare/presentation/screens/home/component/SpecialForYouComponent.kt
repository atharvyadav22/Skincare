package org.aystudios.skincare.presentation.screens.home.component

//@Preview
//@Composable
//fun SpecialForYouComponent() {
//
//    LazyVerticalGrid(columns = GridCells.Fixed(2)) {
//
//        items(10){
//
//            Card(
//                colors = CardDefaults.cardColors(Color.White),
//                modifier = Modifier.padding(4.dp)
//            ) {
//                Column(
//                    modifier = Modifier
//                        .padding(8.dp),
//                ) {
//                    // Square image
//                    Image(
//                        painter = painterResource(R.drawable.dummy),
//                        contentDescription = null,
//                        contentScale = ContentScale.Crop,
//                        modifier = Modifier
//                            .width(144.dp)
//                            .clip(RoundedCornerShape(12.dp))
//                    )
//
//
//                    Text("Gentle Cleanser", style = MaterialTheme.typography.titleMedium, maxLines = 1)
//
//                    Row(
//                        modifier = Modifier
//                            .padding(top = 4.dp),
//                        horizontalArrangement = Arrangement.SpaceBetween,
//                        verticalAlignment = Alignment.CenterVertically
//                    ) {
//                        Row(
//                            verticalAlignment = Alignment.CenterVertically,
//                            horizontalArrangement = Arrangement.spacedBy(8.dp)
//                        ) {
//                            Text(
//                                "₹799",
//                                style = MaterialTheme.typography.bodySmall.copy(
//                                    textDecoration = TextDecoration.LineThrough,
//                                    fontWeight = FontWeight.SemiBold
//                                ),
//                                color = AppPrimaryColor
//                            )
//                            Text("₹599", style = MaterialTheme.typography.titleMedium)
//                        }
//
//
//                    }
//                }
//            }
//        }
//    }
//
//}