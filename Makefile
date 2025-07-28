JAVAC=javac
SRCDIR=src
SRC=$(shell find $(SRCDIR) -name "*.java")
CLASSDIR=class
MAIN=MyApplication

all: $(CLASSDIR)
	$(JAVAC) -sourcepath $(SRCDIR) -d $(CLASSDIR) $(SRC)

$(CLASSDIR):
	mkdir -p $(CLASSDIR)

run: all
	java -cp $(CLASSDIR) core.$(MAIN)

clean:
	rm -rf $(CLASSDIR)
