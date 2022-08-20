array<-c(6, 2, 1, 2, 2, 2, 3, 3, 4, 4, 3, 3, 4, 4, 5, 4, 9, 6, 7, 7, 5, 16, 8, 9, 9, 8, 9, 11, 12, 10)
mat<-c(5, 2, 3, 1, 2, 3, 4, 4, 5, 5, 11, 4, 6, 5, 5, 6, 6, 8, 8, 8, 8, 10, 10, 9, 11, 13, 12, 25, 26, 13)
list<-c(21, 2, 3,3, 6, 5, 4, 4, 5, 3, 6, 10, 8, 6, 8, 7, 9, 8, 10, 16, 9, 10, 11, 11, 12, 14, 14, 15, 22, 17)
vertexNumber<-c(100, 200, 300, 400, 500, 600, 700, 800, 900, 1000, 1100, 1200, 1300, 1400, 1500, 1600, 1700, 1800, 1900, 2000, 2100, 2200, 2300, 2400, 2500, 2600, 2700, 2800, 2900, 3000)
plot(vertexNumber,mat, xlim = c(0,3000) ,ylim  =c(0,100), col = "black", main="Measure the actual time",
     sub="black = adjacency matrix, red = adjacency list, blue = adjacency array", xlab = "vertexNumber", 
     ylab = "time(ms)", pch=0)
abline(lm(mat~vertexNumber), col = "black") #adjacency matrix
par(new = T)
plot(vertexNumber,list, xlim = c(0,3000) ,ylim  =c(0,100), col = "red", main="Measure the actual time",
      xlab = "vertexNumber", ylab = "time(ms)", pch=1)
abline(lm(list~vertexNumber), col = "red") #adjacency list
par(new = T)
plot(vertexNumber,array, xlim = c(0,3000) ,ylim  =c(0,100), col = "blue", main="Measure the actual time",
      xlab = "vertexNumber", ylab = "time(ms)", pch=2)
abline(lm(array~vertexNumber), col = "blue") #adjacency array                                                     