import pandas as pd
import tabula
import numpy as np
import json
import sys, getopt
import argparse
 
def converttide(pdfPath):

  pdf = tabula.read_pdf(""+pdfPath+"", pages="all")
  pagecount =len(pdf) 
  #(pagecount)

  first = []
  schemeFirst = {
    "page": 1,
    "extraction_method": "guess",
    "x1": 29.31871524578892,
    "x2": 563.6040149284062,
    "y1": 460.2443702697754,
    "y2": 758.6404777526856,
    "width": 534.2852996826172,
    "height": 298.39610748291017
  }
  first.append(schemeFirst)

  with open('tideFirst.json', 'w') as outfile:
      json.dump(first, outfile)
  outfile.close()

  readFirst = tabula.read_pdf_with_template(""+pdfPath+"", "tideFirst.json",output_format=None,encoding="utf-8", multiple_tables=False, pages="1", pandas_options={"header":None})

  xyz = []
  for x in range(pagecount-1):
  
    schemeMiddle = {
    "page": x+2,
    "extraction_method": "guess",
    "x1": 35.27175479657017,
    "x2": 563.6040149284062,
    "y1": 178.96325149536133,
    "y2": 746.7343986511231,
    "width": 528.3322601318359,
    "height": 567.7711471557617
    }
    xyz.append(schemeMiddle)
  
  
  with open('tideMiddle.json', 'w') as outfile:
      json.dump(xyz, outfile)
  outfile.close()

  readMiddle = tabula.read_pdf_with_template(""+pdfPath+"", "tideMiddle.json",output_format=None,encoding="utf-8", multiple_tables=False, pandas_options={"header":None})

  df1= pd.DataFrame(np.concatenate(readFirst))
  df2= pd.DataFrame(np.concatenate(readMiddle))
  final = pd.concat([df1, df2], axis=0)

 # print(final)
  final.to_excel(""+pdfPath+".xlsx",header=False)

param1 = sys.argv[1]
converttide(param1)
finalPath = ""+param1+".xlsx"
print(finalPath)