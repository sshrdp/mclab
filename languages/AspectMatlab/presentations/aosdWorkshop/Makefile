.SUFFIXES:
.SUFFIXES: .fig

FIGS=$(wildcard images/figs/*.fig)
OUT_PDF=$(FIGS:fig=pdf)


all: figs workshop_presentation.tex
	cp images/figs/*.pdf images/
	pdflatex workshop_presentation.tex
	pdflatex workshop_presentation.tex

fonce: figs workshop_presentation.tex
	pdflatex workshop_presentation.tex

figs: $(OUT_PDF) copyfigs

copyfigs:
	cp images/figs/*.pdf images/

once: workshop_presentation.tex

	pdflatex workshop_presentation.tex

%.pdf: %.fig
	fig2dev -L pdf $< $@
