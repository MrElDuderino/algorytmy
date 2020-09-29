#include "header.h"

//licznik porownan przy wstawianiu
int fillComparisons = 0;
//licznik porownan przy usuwaniu
int deleteComparisons = 0;


int main()
{
    node *head = NULL;

    //liczba testow
    int n = 100;
    int i;

    srand(time(0));

//    insert(&head, 13);
//    insert(&head, 231);
//    insert(&head, 678);
//    insert(&head, 190);
//    insert(&head, 888);
//    insert(&head, 16212);


//    findTRANS(&head, 888, &fillComparisons);
//    findMTF(&head, 190, &fillComparisons);

    for(i=0; i<n; i++)
    {
        fillList(&head);
        deleteList(&head);
    }

        printList(head);

    double fAverage = (double) fillComparisons/(double)n;
    double cAverage = (double) deleteComparisons/(double)n;

    printf("Fill comparisons  = %d\n", fillComparisons);
    printf("Clean comparisons = %d\n", deleteComparisons);

    printf("Fill comparisons average  = %lf\n", fAverage);
    printf("Clean comparisons average = %lf\n", cAverage);

    return 0;
}


