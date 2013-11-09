#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include <assert.h>
#include <sys/types.h>
#include <unistd.h>
#include <fcntl.h>

#include "sha1.h"

using namespace std;

#define BUFFERSIZE 8192

int main() {
    
    SHA1* sha1;
	  unsigned char* digest;
    #define TEXT1 "abc"
    
    sha1 = new SHA1();
	  sha1->addBytes( TEXT1, strlen( TEXT1 ) );
	  digest = sha1->getDigest();
	  sha1->hexPrinter( digest, 20 );
	  delete sha1;
	  free( digest );
    
    
    return 0;
}
