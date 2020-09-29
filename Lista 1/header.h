#include <stdio.h>
#include <malloc.h>
#include <time.h>

typedef struct node
{
    int value;
    struct node *next;
} node;

//licznik porownan przy wstawianiu
extern int fillComparisons;
//licznik porownan przy usuwaniu
extern int deleteComparisons;


void insert(node **, int);
void delete(node **, int, int *);
int isEmpty(node *);
int findMTF(node **, int, int *);
int findTRANS(node **, int, int *);
int isElementInList(node *, int, int *);
void printList(node *);
void fillList(node **);
void deleteList(node **);
int *randomPermuation(int);
