/*******************************************************************************
 * Copyright 2019 Viridian Software Limited
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/

package org.mini2dx.Tappybird;

import org.mini2Dx.core.Mdx;
import org.mini2Dx.core.graphics.Texture;

public class TopBottomEdgeTexture {

    private static final String GROUND_TEXTURE_LOCATION = "platform01.png";
    // private static final String CEILING_TEXTURE_LOCATION = "ceilingDirt.png";

    Texture groundTexture = Mdx.graphics.newTexture(Mdx.files.internal(GROUND_TEXTURE_LOCATION));
    // Texture ceilingTexture = Mdx.graphics.newTexture(Mdx.files.internal(CEILING_TEXTURE_LOCATION));
}