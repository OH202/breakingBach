breakingBach
========================================================================================================================

File hierarchy is split up into 6 sections in an attempt to organize development according to MVC design principles:

1. bb.controller - contains all controller classes
2. bb.gui.view - contains all styling files for controllers (.fxml and .css)
3. bb.lib - foundation for musicality of program; defines what a note, scale, key etc is.
4. bb.producers - base class for melody, were the controller classes to be taken apart and abstracted, this would be a good place to put it (version 2.0?)
5. bb.strategies - Fitness functions for arranging best melodies
6. bb.util - convenient tools used to create MIDI files, JGAP structures.


MIDI files are stored in the directory in which you downloaded the repo.

========================================================================================================================

The MIT License (MIT)

Copyright (c) 2014 Oliver Herman

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in
all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
THE SOFTWARE.
