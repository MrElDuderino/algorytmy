
param n, integer, >= 2; /*liczba wierzchołków */

/*zbiór wierzchołków V i zbiów krawędzi E*/

set V, default {0..n};

set E, within V cross V;

/*parametr selekcjonujący krawędzie*/
param w{(i,j) in E} > 0;

var a{(i,j) in E}, >= 0;

maximize match: sum{(i,j) in E} a[i,j];


/*gdy krawędź należy do skoajarzenia ma wartość 1 w przeciwnym przypadku 0
 wierzchołek może być końcem co najwyzej jednej krawędzi należącej do skojarzenia więc suma dla wszystkich krawędzi których jest końcem może być co najwyżej równa 1*/
s.t. node{i in V}:
		 sum{(j,i) in E} a[j,i] + sum{(i,j) in E} a[i,j] <= 1;

solve;

data;


