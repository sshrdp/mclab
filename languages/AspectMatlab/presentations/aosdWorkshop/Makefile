.SUFFIXES:
.SUFFIXES: .fig

FIGS=$(wildcard images/figs/*.fig)
OUT_PDF=$(FIGS:fig=pdf)


all: $(OUT_PDF) workshop_presentation.tex
	mv images/figs/*.pdf images/
	pdflatex workshop_presentation.tex
	pdflatex workshop_presentation.tex

%.pdf: %.fig
	fig2dev -L pdf $< $@
