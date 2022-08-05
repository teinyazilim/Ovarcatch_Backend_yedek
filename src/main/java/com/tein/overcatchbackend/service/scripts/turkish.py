import pandas as pd
import tabula
import numpy as np
import json
import sys, getopt
import PyPDF2
from PyPDF2 import PdfFileReader
def convertturkish(pdfPath):

  #pdf = tabula.io.build_options(pages="all", guess=True, lattice=True)
  pdf = PdfFileReader(open(pdfPath,'rb'))
  pagecount = pdf.getNumPages()
  #pdf = tabula.read_pdf(""+pdfPath+"", pages="all", area=[27,565,210,788] , lattice=True, multiple_tables=False)
  #print(pdf)
  #pagecount =len(pdf)
  if pagecount ==0:
    pagecount=1

  print(pagecount)


  xyz = []
  for x in range(pagecount):

    schemeMiddle = {
    "page": x+1,
    "extraction_method": "guess",
    "x1": 27.072495460510254,
    "x2": 565.5474954605103,
    "y1": 210.853125,
    "y2": 788.746875,
    "width": 538.475,
    "height": 577.8937500000001
    }

    xyz.append(schemeMiddle)


  with open('turkish.json', 'w') as outfile:
      json.dump(xyz, outfile)
  outfile.close()

  readMiddle = tabula.read_pdf_with_template(""+pdfPath+"", "turkish.json",output_format=None,encoding="utf-8", multiple_tables=False, pandas_options={"header":None})
  #df1= pd.DataFrame(np.concatenate(readFirst))
  df2= pd.DataFrame(np.concatenate(readMiddle))
  final = pd.concat([df2], axis=0)

  final.to_excel(""+pdfPath+".xlsx",header=False)

param1 = sys.argv[1]
convertturkish(param1)
finalPath = ""+param1+".xlsx"
print(finalPath)