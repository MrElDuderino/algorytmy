#include "header.h"


void insert(node **head, int value)
{
    //nowy wezel
    node *new = malloc(sizeof(node));

    if(new == NULL)
        return;

    new->value = value;
    new->next = NULL;

    //gdy lista jest pusta nowy wezel staje sie pierwszym elementem
    if(*head == NULL)
    {
        *head = new;
        return;
    }

    node *last = *head;

    //iterujemy az do konca listy
    while(last->next != NULL)
    {
        last = last->next;
    }

    last->next = new;
}



void delete(node **head, int value, int *comparisons)
{
    //gdy lista jest pusta: nic do zrobienia
    if(head == NULL)
        return;

    if(*head == NULL)
        return;

    node *current = *head;
    //wezel poprzedzajacy szukany
    node *prev;

    //gdy szukany element jest pierwszym w kolejce usuwamy go i zastepujemy nastepnym
    *comparisons += 1;
    if(current->value == value)
    {
        *head = current->next;
        free(current);
        return;
    }

    prev = current;
    current = current->next;

    //iterujemy po liscie az znajdziemy wezel z szukana wartoscia
    while(current != NULL)
    {
        *comparisons += 1;
        if(current->value == value)
        {
            //usuwamy wezel
            prev->next = current->next;
            free(current);
            return;
        }

        prev = current;
        current = current->next;
    }
}


int isEmpty(node *head)
{
    if(head == NULL)
        return 1;

    else
        return 0;
}



int findMTF(node **head, int value, int *comparisons)
{
    //gdy lista jest pusta: nic do zrobienia
    if(head == NULL)
        return 0;
    if(*head == NULL)
        return 0;

    node *current = *head;
    node *prev;

    *comparisons += 1;
    //gdy szukany wezel jest pierwszy: nic do zrobienia
    if(current->value == value)
        return 1;

    prev = current;
    current = current->next;

    while(current != NULL)
    {
        *comparisons += 1;
        if(current->value == value)
        {
            //glowa listy jest teraz szukany wezel
            prev->next = current->next;
            current->next = *head;
            *head = current;
            return 1;
        }

        prev = current;
        current = current->next;
    }

    return 0;
}


int findTRANS(node **head, int value, int *comparisons)
{
    //gdy lista jest pusta: nic do zrobienia
    if(head == NULL)
        return 0;
    if(*head == NULL)
        return 0;

    node *current = *head;
    //dwa wezy poprzedzajace szukany wezel
    node *prev1, *prev2;

    *comparisons += 1;
    //gdy szukany wezel jest pierwszy: nic do zrobienia
    if(current->value == value)
        return 1;

    prev1 = current;
    current = current->next;

    if(current == NULL)
        return 0;

    *comparisons += 1;
    //gdy szukany wezel jest drugi: zamieniamy jego miejsce z pierwszym
    if(current->value == value)
    {
        prev1->next = current->next;
        current->next = prev1;
        *head = current;
        return 1;
    }

    prev2 = prev1;
    prev1 = current;
    current = current->next;

    //iterujemy az znajdziemy wezel
    while(current != NULL)
    {
        *comparisons += 1;
        if(current->value == value)
        {
            //przesuwamy szukany wezel o jedno miejsce do przodu
            prev2->next = current;
            prev1->next = current->next;
            current->next = prev1;

            return 1;
        }

        prev2 = prev1;
        prev1 = current;
        current = current->next;
    }

    return 0;
}


int isElementInList(node *head, int value, int *comparisons)
{
    if(head == NULL)
        return 0;

    node *current = head;

    while(current != NULL)
    {
        *comparisons += 1;
        if(current->value == value)
            return 1;

        current = current->next;
    }

    return 0;
}


void printList(node *head)
{
    node *p = head;

    while(p != NULL)
    {
        printf("%d\n", p->value);
        p = p->next;
    }
}

void fillList(node **head)
{
    int n = 0;

    int random;
//    srand(time(0));

    while(n<100)
    {
        random = (rand() % (100 - 1 + 1)) + 1;

        if(findMTF(head, random, &fillComparisons) == 0)
//        if(findTRANS(head, random, &fillComparisons) == 0)
        {
            insert(head, random);
            n++;
        }
    }
}

void deleteList(node **head)
{
    int i, max;

    while(isEmpty(*head) == 0)
    {
        for(i=1; i<=100; i++)
        {
//            if(findMTF(head, i, &deleteComparisons) == 1)
            if(findTRANS(head, i, &deleteComparisons) == 1)
                max = i;
        }

        delete(head, max, &deleteComparisons);
    }
}


int *randomPermuation(int n)
{
    int* array = malloc(n * sizeof(int));

    int i;
    for(i=1; i<=n; i++)
    {
        array[i-1] = i;
    }

//    srand(time(0));

    for (int i = n-1; i >= 0; --i)
    {
        int j = rand() % (i + 1 );

        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }

    return array;
}