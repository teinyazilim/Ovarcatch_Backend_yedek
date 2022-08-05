import pandas as pd
import tabula
import numpy as np
import json
import sys, getopt
import argparse
import PyPDF2
from PyPDF2 import PdfFileReader


 
def convertnatwest(pdfPath):
  pdf = PdfFileReader(open(pdfPath,'rb'))
  pagecount = pdf.getNumPages()
#   print(pagecount)


  xyz = []
  for x in range(pagecount):
    schemeMiddle = {
      "page": x+1,
      "extraction_method": "guess",
      "x1": 27.816245460510256,
      "x2": 566.2912454605103,
      "y1": 245.8093721628189,
      "y2": 766.4343721628189,
      "width": 538.475,
      "height": 520.625
    }
    xyz.append(schemeMiddle)
  
  
  with open('natwest.json', 'w') as outfile:
      json.dump(xyz, outfile)
  outfile.close()

  readMiddle = tabula.read_pdf_with_template(""+pdfPath+"", "natwest.json",output_format=None,encoding="utf-8", multiple_tables=False, pandas_options={"header":None})

  #df1= pd.DataFrame(np.concatenate(readFirst))
  df2= pd.DataFrame(np.concatenate(readMiddle))
  final = pd.concat([df2], axis=0)

#   print(final)
  final.to_excel(""+pdfPath+".xlsx",header=False)

param1 = sys.argv[1]
convertnatwest(param1)
finalPath = ""+param1+".xlsx"
print(finalPath)