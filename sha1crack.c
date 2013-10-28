#include <stdio.h> 
#include "sha1.c"
#include "sha1.h"

SHA1Context sha;

int password_length;

int main(int argc, char *argv[]) 
{
    password_length = atoi(argv[0]);
    
    printf("Length = " + password_length);    
    
    return 0;  
}
