import pandas as pd
import tabula
import numpy as np
import json
import sys, getopt
import argparse
 
def convertmonese(pdfPath):

  pdf = tabula.read_pdf(""+pdfPath+"", pages="all")
  pagecount =len(pdf) +1
  #print(pagecount)

  abc = []
  schemeFirst = {
      "page": 1,
      "extraction_method": "guess",
      "x1": 19.644236425170675,
      "x2": 578.463363927612,
      "y1": 594.1638792419433,
      "y2": 772.0037879943848,
      "width": 558.8191275024413,
      "height": 177.8399087524414
      }
  abc.append(schemeFirst)

  with open('monsoeFirst.json', 'w') as outfile:
      json.dump(abc, outfile)
  outfile.close()


  readFirst = tabula.read_pdf_with_template(""+pdfPath+"", "monsoeFirst.json",output_format=None,encoding="utf-8", multiple_tables=False, pandas_options={"header":None})

  xyz = []
  for x in range(pagecount-1):
  
    scheme = {
      "page": x+2,
      "extraction_method": "guess",
      "x1": 27.08523679138161,
      "x2": 575.4869637811277,
      "y1": 22.695051116943358,
      "y2": 807.7205897521972,
      "width": 548.4017269897461,
      "height": 785.0255386352538
      }
    xyz.append(scheme)
  
  
  with open('monsoeMiddle.json', 'w') as outfile:
      json.dump(xyz, outfile)
  outfile.close()

  readMiddle = tabula.read_pdf_with_template(""+pdfPath+"", "monsoeMiddle.json",output_format=None,encoding="utf-8", multiple_tables=False, pandas_options={"header":None})

  df1= pd.DataFrame(np.concatenate(readFirst))
  df2= pd.DataFrame(np.concatenate(readMiddle))
  final = pd.concat([df1, df2], axis=0)

  #print(final)
  final.to_excel(""+pdfPath+".xlsx",header=False)


param1 = sys.argv[1]
convertmonese(param1)
finalPath = ""+param1+".xlsx"
print(finalPath)