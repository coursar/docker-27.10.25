#!/bin/bash

as -gstabs 01_pause.S -o 01_pause.o && ld 01_pause.o -o 01_pause
